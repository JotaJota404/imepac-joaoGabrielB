package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class TelaPrincipal : AppCompatActivity() {

    private lateinit var text_boas_vindas: TextView
    private lateinit var bt_cadastrar_livro: LinearLayout
    private lateinit var bt_lista_livros: LinearLayout
    private lateinit var bt_meu_perfil: LinearLayout
    private lateinit var bt_gerar_seed: LinearLayout

    private val db = FirebaseFirestore.getInstance()

    // Dados fictícios para o Seed
    private val livrosSeed = listOf(
        Triple("O Senhor dos Anéis", "J.R.R. Tolkien", "1954"),
        Triple("Harry Potter e a Pedra Filosofal", "J.K. Rowling", "1997"),
        Triple("Dom Quixote", "Miguel de Cervantes", "1605"),
        Triple("Cem Anos de Solidão", "Gabriel García Márquez", "1967"),
        Triple("Crime e Castigo", "Fiódor Dostoiévski", "1866"),
        Triple("1984", "George Orwell", "1949"),
        Triple("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "1943"),
        Triple("Orgulho e Preconceito", "Jane Austen", "1813"),
        Triple("A Metamorfose", "Franz Kafka", "1915"),
        Triple("Moby Dick", "Herman Melville", "1851"),
        Triple("A Divina Comédia", "Dante Alighieri", "1320"),
        Triple("O Alquimista", "Paulo Coelho", "1988"),
        Triple("Sapiens", "Yuval Noah Harari", "2011"),
        Triple("O Hobbit", "J.R.R. Tolkien", "1937"),
        Triple("Fundação", "Isaac Asimov", "1951")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        supportActionBar?.hide()
        IniciarComponentes()

        bt_cadastrar_livro.setOnClickListener {
            val intent = Intent(this, FormCadastroLivro::class.java)
            startActivity(intent)
        }

        bt_lista_livros.setOnClickListener {
            val intent = Intent(this, TelaListaLivros::class.java)
            startActivity(intent)
        }

        bt_meu_perfil.setOnClickListener {
            val intent = Intent(this, TelaPerfil::class.java)
            startActivity(intent)
        }

        bt_gerar_seed.setOnClickListener { view ->
            executarSeed(view)
        }
    }

    private fun executarSeed(view: View) {
        view.isEnabled = false
        view.alpha = 0.3f

        var contadorSucesso = 0
        var contadorErro = 0
        val totalItens = livrosSeed.size

        // Loop que insere os 15 documentos fictícios na coleção "Livros"
        for ((titulo, autor, ano) in livrosSeed) {
            val dadosLivro = hashMapOf(
                "titulo" to titulo,
                "autor" to autor,
                "ano" to ano,
                "timestamp" to Date() // Cada documento recebe o timestamp atual
            )

            db.collection("Livros")
                .add(dadosLivro)
                .addOnSuccessListener {
                    contadorSucesso++
                    // Só exibe o Snackbar quando todos os documentos forem processados
                    if (contadorSucesso + contadorErro == totalItens) {
                        view.isEnabled = true
                        view.alpha = 0.6f
                        Snackbar.make(
                            view,
                            "$contadorSucesso livros inseridos com sucesso!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                .addOnFailureListener {
                    contadorErro++
                    if (contadorSucesso + contadorErro == totalItens) {
                        view.isEnabled = true
                        view.alpha = 0.6f
                        Snackbar.make(
                            view,
                            "$contadorSucesso inseridos, $contadorErro com erro.",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun IniciarComponentes() {
        text_boas_vindas = findViewById(R.id.text_boas_vindas)
        bt_cadastrar_livro = findViewById(R.id.bt_cadastrar_livro)
        bt_lista_livros = findViewById(R.id.bt_lista_livros)
        bt_meu_perfil = findViewById(R.id.bt_meu_perfil)
        bt_gerar_seed = findViewById(R.id.bt_gerar_seed)
    }
}
