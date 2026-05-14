package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_entrar: Button
    private lateinit var progressbar: ProgressBar
    private lateinit var text_tela_cadastro: TextView

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        supportActionBar?.hide()
        IniciarComponentes()

        text_tela_cadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        bt_entrar.setOnClickListener {
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                AutenticarUsuario(it)
            }
        }
    }

    private fun AutenticarUsuario(view: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()

        progressbar.visibility = View.VISIBLE
        bt_entrar.isEnabled = false

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                irParaTelaPrincipal()
            } else {
                progressbar.visibility = View.GONE
                bt_entrar.isEnabled = true
                Snackbar.make(view, "Erro ao logar!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun irParaTelaPrincipal() {
        val intent = Intent(this, TelaPerfil::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = auth.currentUser
        if (usuarioAtual != null) {
            irParaTelaPrincipal()
        }
    }

    private fun IniciarComponentes() {
        edit_email = findViewById(R.id.edit_email_login)
        edit_senha = findViewById(R.id.edit_senha_login)
        bt_entrar = findViewById(R.id.bt_entrar_login)
        progressbar = findViewById(R.id.progressbar_login)
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro_login)
    }
}
