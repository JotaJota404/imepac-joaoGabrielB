package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    private lateinit var textNomeUser: EditText
    private lateinit var textEmailUser: EditText
    private lateinit var bt_sair: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_sair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUsuario()
    }

    private fun recuperarDadosUsuario() {
        val email = auth.currentUser?.email ?: return

        db.collection("Usuarios").whereEqualTo("email", email)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) return@addSnapshotListener
                val document = querySnapshot?.documents?.firstOrNull()
                if (document != null) {
                    textNomeUser.setText(document.getString("nome"))
                    textEmailUser.setText(document.getString("email"))
                }
            }
    }

    private fun IniciarComponentes() {
        textNomeUser = findViewById(R.id.textNomeUser)
        textEmailUser = findViewById(R.id.textEmailUser)
        bt_sair = findViewById(R.id.bt_sair)
    }
}
