package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class TelaDetalheLivro : AppCompatActivity() {

    private lateinit var edit_titulo_detalhe: EditText
    private lateinit var edit_autor_detalhe: EditText
    private lateinit var edit_ano_detalhe: EditText
    private lateinit var bt_salvar_alteracoes: Button
    private lateinit var bt_excluir_livro: Button
    private lateinit var progressbar_detalhe: ProgressBar

    private val db = FirebaseFirestore.getInstance()

    // ID do documento recebido pelo Intent
    private var livroId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_detalhe_livro)

        supportActionBar?.hide()
        IniciarComponentes()

        // Recebe o ID do documento enviado pela TelaListaLivros
        livroId = intent.getStringExtra("LIVRO_ID") ?: ""

        if (livroId.isEmpty()) {
            Snackbar.make(bt_salvar_alteracoes, "Erro: ID do livro não encontrado.", Snackbar.LENGTH_LONG).show()
            finish()
            return
        }

        // Busca os dados do livro logo no inicio
        buscarDadosLivro()

        bt_salvar_alteracoes.setOnClickListener { view ->
            val titulo = edit_titulo_detalhe.text.toString().trim()
            val autor = edit_autor_detalhe.text.toString().trim()
            val ano = edit_ano_detalhe.text.toString().trim()

            if (titulo.isEmpty() || autor.isEmpty() || ano.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                salvarAlteracoes(view, titulo, autor, ano)
            }
        }

        bt_excluir_livro.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Excluir Livro")
                .setMessage("Tem certeza que deseja excluir este livro? Esta ação não pode ser desfeita.")
                .setPositiveButton("Excluir") { _, _ ->
                    excluirLivro(view)
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    private fun buscarDadosLivro() {
        progressbar_detalhe.visibility = View.VISIBLE
        bt_salvar_alteracoes.isEnabled = false

        // SELECT de 1 registro: busca diretamente pelo ID do documento
        db.collection("Livros").document(livroId)
            .get()
            .addOnSuccessListener { document ->
                progressbar_detalhe.visibility = View.GONE
                bt_salvar_alteracoes.isEnabled = true

                if (document != null && document.exists()) {
                    // Preenche os EditTexts com os dados vindos do Firestore
                    edit_titulo_detalhe.setText(document.getString("titulo"))
                    edit_autor_detalhe.setText(document.getString("autor"))
                    edit_ano_detalhe.setText(document.getString("ano"))
                } else {
                    Snackbar.make(
                        bt_salvar_alteracoes,
                        "Livro não encontrado no banco de dados.",
                        Snackbar.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                progressbar_detalhe.visibility = View.GONE
                bt_salvar_alteracoes.isEnabled = true
                Snackbar.make(
                    bt_salvar_alteracoes,
                    "Erro ao buscar livro: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun salvarAlteracoes(view: View, titulo: String, autor: String, ano: String) {
        progressbar_detalhe.visibility = View.VISIBLE
        bt_salvar_alteracoes.isEnabled = false

        // Mapa com apenas os campos que serão atualizados (UPDATE parcial)
        val dadosAtualizados = mapOf(
            "titulo" to titulo,
            "autor" to autor,
            "ano" to ano
        )

        // UPDATE: usa update() no documento específico pelo livroId
        db.collection("Livros").document(livroId)
            .update(dadosAtualizados)
            .addOnSuccessListener {
                progressbar_detalhe.visibility = View.GONE
                bt_salvar_alteracoes.isEnabled = true
                Snackbar.make(view, "Livro atualizado com sucesso!", Snackbar.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                progressbar_detalhe.visibility = View.GONE
                bt_salvar_alteracoes.isEnabled = true
                Snackbar.make(
                    view,
                    "Erro ao atualizar livro: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun excluirLivro(view: View) {
        progressbar_detalhe.visibility = View.VISIBLE
        bt_excluir_livro.isEnabled = false
        bt_salvar_alteracoes.isEnabled = false

        // DELETE: remove o documento pelo ID
        db.collection("Livros").document(livroId)
            .delete()
            .addOnSuccessListener {
                Snackbar.make(view, "Livro excluído com sucesso!", Snackbar.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                progressbar_detalhe.visibility = View.GONE
                bt_excluir_livro.isEnabled = true
                bt_salvar_alteracoes.isEnabled = true
                Snackbar.make(view, "Erro ao excluir: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
    }

    private fun IniciarComponentes() {
        edit_titulo_detalhe = findViewById(R.id.edit_titulo_detalhe)
        edit_autor_detalhe = findViewById(R.id.edit_autor_detalhe)
        edit_ano_detalhe = findViewById(R.id.edit_ano_detalhe)
        bt_salvar_alteracoes = findViewById(R.id.bt_salvar_alteracoes)
        bt_excluir_livro = findViewById(R.id.bt_excluir_livro)
        progressbar_detalhe = findViewById(R.id.progressbar_detalhe)
    }
}
