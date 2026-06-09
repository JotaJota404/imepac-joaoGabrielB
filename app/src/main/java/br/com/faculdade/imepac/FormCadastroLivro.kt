package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class FormCadastroLivro : AppCompatActivity() {

    private lateinit var edit_titulo_livro: EditText
    private lateinit var edit_autor_livro: EditText
    private lateinit var edit_ano_livro: EditText
    private lateinit var bt_salvar_livro: Button
    private lateinit var progressbar_cadastro_livro: ProgressBar
    private lateinit var bt_voltar: ImageView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro_livro)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_voltar.setOnClickListener {
            finish()
        }

        bt_salvar_livro.setOnClickListener { view ->
            val titulo = edit_titulo_livro.text.toString().trim()
            val autor = edit_autor_livro.text.toString().trim()
            val ano = edit_ano_livro.text.toString().trim()

            if (titulo.isEmpty() || autor.isEmpty() || ano.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                salvarLivro(view, titulo, autor, ano)
            }
        }
    }

    private fun salvarLivro(view: View, titulo: String, autor: String, ano: String) {
        bt_salvar_livro.isEnabled = false
        progressbar_cadastro_livro.visibility = View.VISIBLE

        val dadosLivro = hashMapOf(
            "titulo" to titulo,
            "autor" to autor,
            "ano" to ano,
            "timestamp" to Date()
        )

        db.collection("Livros")
            .add(dadosLivro)
            .addOnSuccessListener {
                progressbar_cadastro_livro.visibility = View.GONE
                bt_salvar_livro.isEnabled = true
                Snackbar.make(view, "Livro cadastrado com sucesso!", Snackbar.LENGTH_LONG).show()
                limparCampos()
            }
            .addOnFailureListener { e ->
                progressbar_cadastro_livro.visibility = View.GONE
                bt_salvar_livro.isEnabled = true
                Snackbar.make(
                    view,
                    "Erro ao cadastrar livro: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun limparCampos() {
        edit_titulo_livro.text.clear()
        edit_autor_livro.text.clear()
        edit_ano_livro.text.clear()
        edit_titulo_livro.requestFocus()
    }

    private fun IniciarComponentes() {
        edit_titulo_livro = findViewById(R.id.edit_titulo_livro)
        edit_autor_livro = findViewById(R.id.edit_autor_livro)
        edit_ano_livro = findViewById(R.id.edit_ano_livro)
        bt_salvar_livro = findViewById(R.id.bt_salvar_livro)
        progressbar_cadastro_livro = findViewById(R.id.progressbar_cadastro_livro)
        bt_voltar = findViewById(R.id.bt_voltar_cadastro_livro)
    }
}
