package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Tela_Perfil : AppCompatActivity() {

    private lateinit var text_nome: TextView
    private lateinit var text_email: TextView
    private lateinit var bt_deslogar: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_deslogar.setOnClickListener {
            auth.signOut()
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
        val usuarioID = auth.currentUser?.uid ?: return

        db.collection("Usuarios").document(usuarioID)
            .addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    text_nome.text = documentSnapshot.getString("nome")
                    text_email.text = documentSnapshot.getString("email")
                }
            }
    }

    private fun IniciarComponentes() {
        text_nome = findViewById(R.id.text_nome_usuario)
        text_email = findViewById(R.id.text_email_usuario)
        bt_deslogar = findViewById(R.id.bt_deslogar)
    }
}
