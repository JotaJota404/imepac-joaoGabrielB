package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_cadastrar: Button
    private lateinit var progressbar: ProgressBar

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_cadastrar.setOnClickListener { view ->
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(view, nome, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(view: View, nome: String, email: String, senha: String) {
        // Desativar botão e mostrar progresso para evitar cliques duplos
        bt_cadastrar.isEnabled = false
        progressbar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Firebase criou o utilizador — agora gravar no Firestore
                    salvarDadosUsuario(view, nome)
                } else {
                    // Reativar UI
                    bt_cadastrar.isEnabled = true
                    progressbar.visibility = View.GONE

                    // Capturar o erro REAL do Firebase e exibir ao utilizador
                    val mensagemErro = when (task.exception) {
                        is FirebaseAuthUserCollisionException ->
                            "Este e-mail já está em uso por outra conta."
                        is FirebaseAuthWeakPasswordException ->
                            "Senha fraca: use no mínimo 6 caracteres."
                        is FirebaseAuthInvalidCredentialsException ->
                            "Formato de e-mail inválido."
                        else ->
                            "Erro ao cadastrar: ${task.exception?.message ?: "Tente novamente."}"
                    }
                    Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun salvarDadosUsuario(view: View, nome: String) {
        val usuario = auth.currentUser

        // Proteção extra: se por algum motivo o currentUser for nulo, abortar
        if (usuario == null) {
            bt_cadastrar.isEnabled = true
            progressbar.visibility = View.GONE
            Snackbar.make(view, "Erro interno: utilizador não encontrado após registo.", Snackbar.LENGTH_LONG).show()
            return
        }

        val email = usuario.email ?: ""
        val usuarioID = usuario.uid

        val dadosUsuario = hashMapOf(
            "nome" to nome,
            "email" to email,
            "uid" to usuarioID
        )

        db.collection("Usuarios").document(usuarioID).set(dadosUsuario)
            .addOnSuccessListener {
                // Tudo concluído com sucesso — fechar e voltar para o Login
                Snackbar.make(view, "Cadastro realizado com sucesso!", Snackbar.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                // Auth funcionou mas Firestore falhou — reativar UI e informar
                bt_cadastrar.isEnabled = true
                progressbar.visibility = View.GONE
                Snackbar.make(
                    view,
                    "Conta criada, mas erro ao salvar dados: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun IniciarComponentes() {
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        bt_cadastrar = findViewById(R.id.bt_cadastrar)
        progressbar = findViewById(R.id.progressbar_cadastro)
    }
}
