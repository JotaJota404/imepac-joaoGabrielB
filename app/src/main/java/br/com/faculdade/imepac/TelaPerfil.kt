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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    private lateinit var textNomeUser: TextView
    private lateinit var textEmailUser: TextView
    private lateinit var bt_sair: Button
    private lateinit var ic_person: ImageView
    private lateinit var container_avatar: FrameLayout
    private lateinit var bt_voltar: ImageView

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
            // Solicita permissão persistente para ler o URI mesmo após reiniciar o app
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                // Alguns URIs (ex: Google Fotos temporários) não suportam persistência
                // A foto ainda será carregada na sessão atual
            }
            // Define a imagem no ImageView
            ic_person.setImageURI(it)
            ic_person.clearColorFilter() // Remove o tint após selecionar foto real
            ic_person.setPadding(0, 0, 0, 0)
            ic_person.scaleType = ImageView.ScaleType.CENTER_CROP
            // Salva o URI no SharedPreferences para persistência
            prefs.edit().putString(PREF_KEY_FOTO, it.toString()).apply()
        }
    }

    // Launcher para solicitar permissão de mídia
    private val solicitarPermissaoLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedida ->
        if (concedida) {
            abrirGaleria()
        } else {
            Toast.makeText(
                this,
                "Permissão necessária para acessar a galeria",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        supportActionBar?.hide()
        prefs = getSharedPreferences("imepac_prefs", MODE_PRIVATE)
        IniciarComponentes()

        // Carrega foto salva anteriormente
        carregarFotoSalva()

        bt_voltar.setOnClickListener {
            finish()
        }

        bt_sair.setOnClickListener {
            // Limpa a foto ao sair (opcional — mantemos para não mostrar foto de outro usuário)
            prefs.edit().remove(PREF_KEY_FOTO).apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }

        // Ao tocar no avatar, abre o seletor de imagem
        container_avatar.setOnClickListener {
            verificarPermissaoEAbrirGaleria()
        }
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUsuario()
    }

    private fun carregarFotoSalva() {
        val uriString = prefs.getString(PREF_KEY_FOTO, null)
        if (uriString != null) {
            try {
                val uri = Uri.parse(uriString)
                ic_person.setImageURI(uri)
                ic_person.clearColorFilter()
                ic_person.setPadding(0, 0, 0, 0)
                ic_person.scaleType = ImageView.ScaleType.CENTER_CROP
            } catch (e: Exception) {
                // URI inválido ou expirado — mantém ícone padrão
                prefs.edit().remove(PREF_KEY_FOTO).apply()
            }
        }
    }

    private fun verificarPermissaoEAbrirGaleria() {
        val permissao = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(this, permissao) == PackageManager.PERMISSION_GRANTED -> {
                abrirGaleria()
            }
            else -> {
                solicitarPermissaoLauncher.launch(permissao)
            }
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
                    textNomeUser.setText(document.getString("nome"))
                    textEmailUser.setText(document.getString("email"))
                }
            }
            .addOnFailureListener {
                println("Erro ao recuperar dados do utilizador")
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
