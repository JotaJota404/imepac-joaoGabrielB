package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class TelaBuscaDireta : AppCompatActivity() {

    private lateinit var edit_busca_direta: EditText
    private lateinit var bt_buscar: Button
    private lateinit var progressbar_busca: ProgressBar
    private lateinit var card_resultado: LinearLayout
    private lateinit var text_resultado_titulo: TextView
    private lateinit var text_resultado_autor: TextView
    private lateinit var text_resultado_ano: TextView
    private lateinit var text_nenhum_resultado: TextView
    private lateinit var bt_voltar: ImageView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_busca_direta)

        supportActionBar?.hide()
        iniciarComponentes()

        bt_voltar.setOnClickListener { finish() }

        bt_buscar.setOnClickListener { view ->
            executarBusca(view)
        }

        // Também dispara ao pressionar "Buscar" no teclado
        edit_busca_direta.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                executarBusca(bt_buscar)
                true
            } else false
        }
    }

    private fun executarBusca(view: View) {
        val titulo = edit_busca_direta.text.toString().trim()

        if (titulo.isEmpty()) {
            Snackbar.make(view, "Digite o título do livro para buscar.", Snackbar.LENGTH_LONG).show()
            return
        }

        // Esconde o teclado
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        // Reseta o estado da UI
        card_resultado.visibility = View.GONE
        text_nenhum_resultado.visibility = View.GONE
        progressbar_busca.visibility = View.VISIBLE
        bt_buscar.isEnabled = false

        /*
         * SELECT com WHERE: busca o PRIMEIRO livro cujo título começa com o texto digitado.
         * Usa >= e <= com o caractere \uf8ff (high surrogate) para simular "startsWith"
         * no Firestore — retorna apenas 1 registro via .limit(1).
         */
        val fim = titulo + "\uF8FF"
        db.collection("Livros")
            .whereGreaterThanOrEqualTo("titulo", titulo)
            .whereLessThanOrEqualTo("titulo", fim)
            .limit(1)  // ← SELECT de apenas 1 registro
            .get()
            .addOnSuccessListener { querySnapshot ->
                progressbar_busca.visibility = View.GONE
                bt_buscar.isEnabled = true

                if (querySnapshot.isEmpty) {
                    text_nenhum_resultado.visibility = View.VISIBLE
                } else {
                    // Exibe o único registro encontrado
                    val doc = querySnapshot.documents.first()
                    text_resultado_titulo.text = doc.getString("titulo") ?: "—"
                    text_resultado_autor.text  = doc.getString("autor")  ?: "—"
                    text_resultado_ano.text    = doc.getString("ano")    ?: "—"
                    card_resultado.visibility  = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                progressbar_busca.visibility = View.GONE
                bt_buscar.isEnabled = true
                Snackbar.make(
                    view,
                    "Erro na busca: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun iniciarComponentes() {
        edit_busca_direta       = findViewById(R.id.edit_busca_direta)
        bt_buscar               = findViewById(R.id.bt_buscar)
        progressbar_busca       = findViewById(R.id.progressbar_busca)
        card_resultado          = findViewById(R.id.card_resultado)
        text_resultado_titulo   = findViewById(R.id.text_resultado_titulo)
        text_resultado_autor    = findViewById(R.id.text_resultado_autor)
        text_resultado_ano      = findViewById(R.id.text_resultado_ano)
        text_nenhum_resultado   = findViewById(R.id.text_nenhum_resultado)
        bt_voltar               = findViewById(R.id.bt_voltar_busca)
    }
}
