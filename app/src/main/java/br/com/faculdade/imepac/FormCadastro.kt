package br.com.faculdade.imepac

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.faculdade.imepac.databinding.ActivityFormCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btCadastrar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        salvarDadosUsuario(nome, email)
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun salvarDadosUsuario(nome: String, email: String) {
        val usuarioID = auth.currentUser?.uid ?: return
        
        val usuarios = mutableMapOf<String, Any>()
        usuarios["nome"] = nome
        usuarios["email"] = email
        usuarios["uid"] = usuarioID

        db.collection("Usuarios").document(usuarioID)
            .set(usuarios)
            .addOnSuccessListener {
                Toast.makeText(this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar dados!", Toast.LENGTH_SHORT).show()
            }
    }
}
