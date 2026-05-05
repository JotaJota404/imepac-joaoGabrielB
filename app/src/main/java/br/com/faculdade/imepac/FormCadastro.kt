package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_cadastrar: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_cadastrar.setOnClickListener {
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(it)
            }
        }
    }

    private fun cadastrarUsuario(view: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                salvarDadosUsuario()
                Snackbar.make(view, "Sucesso ao cadastrar usuário!", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "Erro ao cadastrar usuário!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun salvarDadosUsuario() {
        val nome = edit_nome.text.toString().trim()
        val email = FirebaseAuth.getInstance().currentUser?.email
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        val usuarios = hashMapOf(
            "nome" to nome,
            "email" to email,
            "uid" to usuarioID
        )

        db.collection("Usuarios").add(usuarios)
            .addOnSuccessListener {
                println("Sucesso ao salvar os dados")
                finish()
            }
            .addOnFailureListener {
                println("Erro ao salvar os dados")
            }
    }

    private fun IniciarComponentes() {
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        bt_cadastrar = findViewById(R.id.bt_cadastrar)
    }
}
