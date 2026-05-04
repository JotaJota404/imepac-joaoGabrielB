package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.faculdade.imepac.databinding.ActivityTelaPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Tela_Perfil : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPerfilBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btDeslogar.setOnClickListener {
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
                    binding.textNomeUsuario.text = documentSnapshot.getString("nome")
                    binding.textEmailUsuario.text = documentSnapshot.getString("email")
                }
            }
    }
}
