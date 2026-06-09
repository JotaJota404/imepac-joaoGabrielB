package br.com.faculdade.imepac

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    private lateinit var textNomeUser: TextView
    private lateinit var textEmailUser: TextView
    private lateinit var bt_sair: Button
    private lateinit var ic_person: ShapeableImageView   // ShapeableImageView faz clip circular real
    private lateinit var container_avatar: FrameLayout
    private lateinit var bt_voltar: android.widget.ImageView

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // SharedPreferences para persistência local da foto de perfil
    private lateinit var prefs: SharedPreferences
    private val PREF_KEY_FOTO = "foto_perfil_uri"

    // Launcher para selecionar imagem da galeria
    private val selecionarFotoLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Tenta obter permissão persistente de leitura do URI (nem todo URI suporta)
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {
                // Alguns provedores (ex: Google Fotos temp.) não suportam — a foto
                // ainda funciona na sessão atual, mas pode não persistir entre reinícios.
            }
            aplicarFoto(it)
            // Salva o URI para persistência entre sessões
            prefs.edit().putString(PREF_KEY_FOTO, it.toString()).apply()
        }
    }

    // Launcher para solicitar permissão de acesso à galeria
    private val solicitarPermissaoLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedida ->
        if (concedida) {
            abrirGaleria()
        } else {
            Toast.makeText(
                this,
                "Permissão de galeria necessária para alterar a foto",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        supportActionBar?.hide()
        prefs = getSharedPreferences("livraria_pessoal_prefs", MODE_PRIVATE)
        IniciarComponentes()

        // Carrega foto salva anteriormente (se houver)
        carregarFotoSalva()

        bt_voltar.setOnClickListener { finish() }

        bt_sair.setOnClickListener {
            // Remove a foto salva ao sair — evita exibir foto de outro usuário
            prefs.edit().remove(PREF_KEY_FOTO).apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            // Limpa o back stack para o usuário não voltar sem autenticar
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Abre seletor de imagem ao tocar no avatar
        container_avatar.setOnClickListener {
            verificarPermissaoEAbrirGaleria()
        }
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUsuario()
    }

    /**
     * Aplica a foto selecionada no ShapeableImageView.
     * Remove qualquer tint/filtro de cor anterior e garante scaleType correto.
     */
    private fun aplicarFoto(uri: Uri) {
        ic_person.setImageURI(uri)
        // Crítico: remove o tint âmbar definido no ícone padrão via código (se houver)
        // imageTintList = null é o método correto para ShapeableImageView
        ic_person.imageTintList = null
        ic_person.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
        // Remove o padding que era necessário apenas para o ícone genérico
        ic_person.setPadding(0, 0, 0, 0)
    }

    private fun carregarFotoSalva() {
        val uriString = prefs.getString(PREF_KEY_FOTO, null) ?: return
        try {
            val uri = Uri.parse(uriString)
            // Verifica se o URI ainda é acessível antes de exibir
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    aplicarFoto(uri)
                }
            }
        } catch (e: Exception) {
            // URI expirado ou inválido — limpa e mantém ícone padrão
            prefs.edit().remove(PREF_KEY_FOTO).apply()
        }
    }

    private fun verificarPermissaoEAbrirGaleria() {
        val permissao = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (ContextCompat.checkSelfPermission(this, permissao) == PackageManager.PERMISSION_GRANTED) {
            abrirGaleria()
        } else {
            solicitarPermissaoLauncher.launch(permissao)
        }
    }

    private fun abrirGaleria() {
        selecionarFotoLauncher.launch("image/*")
    }

    private fun recuperarDadosUsuario() {
        val email = auth.currentUser?.email ?: return
        db.collection("Usuarios").whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val document = querySnapshot.documents.firstOrNull()
                if (document != null) {
                    textNomeUser.text = document.getString("nome") ?: ""
                    textEmailUser.text = document.getString("email") ?: ""
                }
            }
            .addOnFailureListener {
                // Falha silenciosa — dados serão carregados no próximo onStart
            }
    }

    private fun IniciarComponentes() {
        textNomeUser = findViewById(R.id.textNomeUser)
        textEmailUser = findViewById(R.id.textEmailUser)
        bt_sair = findViewById(R.id.bt_sair)
        ic_person = findViewById(R.id.ic_person)
        container_avatar = findViewById(R.id.container_avatar)
        bt_voltar = findViewById(R.id.bt_voltar_perfil)
    }
}
