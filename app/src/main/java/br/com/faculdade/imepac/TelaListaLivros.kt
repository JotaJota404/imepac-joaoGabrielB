package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TelaListaLivros : AppCompatActivity() {

    private lateinit var recycler_livros: RecyclerView
    private lateinit var progressbar_lista: ProgressBar
    private lateinit var progressbar_paginacao: ProgressBar
    private lateinit var edit_busca_livro: EditText
    private lateinit var bt_limpar_busca: ImageView
    private lateinit var text_sem_resultado: TextView
    private lateinit var bt_voltar: ImageView

    private val db = FirebaseFirestore.getInstance()

    // Lista de dados e adapter
    private val listaLivros = mutableListOf<Livro>()
    private lateinit var adapterLivros: AdapterLivros

    // Controle de paginação
    private val TAMANHO_PAGINA = 10L
    private var ultimoDocumento: DocumentSnapshot? = null
    private var carregando = false
    private var semMaisDados = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_lista_livros)

        supportActionBar?.hide()
        IniciarComponentes()
        configurarRecyclerView()
        configurarBusca()
        carregarPrimeiraPagina()

        bt_voltar.setOnClickListener {
            finish()
        }
    }

    private fun configurarBusca() {
        edit_busca_livro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString() ?: ""
                // Mostra/esconde o botão de limpar
                bt_limpar_busca.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                // Filtra a lista
                adapterLivros.filtrar(query)
                // Mostra mensagem se nenhum resultado
                text_sem_resultado.visibility =
                    if (adapterLivros.itemCount == 0 && query.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })

        bt_limpar_busca.setOnClickListener {
            edit_busca_livro.text.clear()
            edit_busca_livro.clearFocus()
            bt_limpar_busca.visibility = View.GONE
            text_sem_resultado.visibility = View.GONE
        }
    }

    private fun configurarRecyclerView() {
        adapterLivros = AdapterLivros(listaLivros) { livroClicado ->
            // Ao clicar em um item, abre TelaDetalheLivro passando o ID do documento
            val intent = Intent(this, TelaDetalheLivro::class.java)
            intent.putExtra("LIVRO_ID", livroClicado.id)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        recycler_livros.layoutManager = layoutManager
        recycler_livros.adapter = adapterLivros

        // Listener de scroll para detectar quando chegou no fim da lista (paginação)
        recycler_livros.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Só carrega mais se estiver rolando para baixo e não estiver em modo de busca
                if (dy <= 0) return
                if (edit_busca_livro.text.isNotEmpty()) return

                val totalItens = layoutManager.itemCount
                val ultimoVisivel = layoutManager.findLastVisibleItemPosition()

                // Se os 3 últimos itens ficaram visíveis e não está carregando e tem mais dados
                if (!carregando && !semMaisDados && ultimoVisivel >= totalItens - 3) {
                    carregarProximaPagina()
                }
            }
        })
    }

    private fun carregarPrimeiraPagina() {
        progressbar_lista.visibility = View.VISIBLE
        carregando = true

        db.collection("Livros")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(TAMANHO_PAGINA)
            .get()
            .addOnSuccessListener { querySnapshot ->
                progressbar_lista.visibility = View.GONE
                carregando = false

                if (querySnapshot.isEmpty) {
                    semMaisDados = true
                    return@addOnSuccessListener
                }

                // Guarda o último documento para usar como cursor na próxima página
                ultimoDocumento = querySnapshot.documents.last()

                val novosLivros = querySnapshot.documents.map { doc ->
                    Livro(
                        id = doc.id,
                        titulo = doc.getString("titulo") ?: "Sem título",
                        autor = doc.getString("autor") ?: "Sem autor",
                        ano = doc.getString("ano") ?: ""
                    )
                }

                adapterLivros.adicionarItens(novosLivros)

                // Se veio menos que o limite, não há mais páginas
                if (querySnapshot.size() < TAMANHO_PAGINA) {
                    semMaisDados = true
                }
            }
            .addOnFailureListener { e ->
                progressbar_lista.visibility = View.GONE
                carregando = false
                Snackbar.make(
                    recycler_livros,
                    "Erro ao carregar livros: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun carregarProximaPagina() {
        // Se não há cursor ou não tem mais dados, não faz nada
        val cursor = ultimoDocumento ?: return
        if (semMaisDados) return

        progressbar_paginacao.visibility = View.VISIBLE
        carregando = true

        db.collection("Livros")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .startAfter(cursor)   // <- Paginação: começa após o último documento carregado
            .limit(TAMANHO_PAGINA)
            .get()
            .addOnSuccessListener { querySnapshot ->
                progressbar_paginacao.visibility = View.GONE
                carregando = false

                if (querySnapshot.isEmpty) {
                    semMaisDados = true
                    return@addOnSuccessListener
                }

                // Atualiza o cursor para a próxima chamada
                ultimoDocumento = querySnapshot.documents.last()

                val novosLivros = querySnapshot.documents.map { doc ->
                    Livro(
                        id = doc.id,
                        titulo = doc.getString("titulo") ?: "Sem título",
                        autor = doc.getString("autor") ?: "Sem autor",
                        ano = doc.getString("ano") ?: ""
                    )
                }

                adapterLivros.adicionarItens(novosLivros)

                // Se veio menos que o limite, chegou no fim
                if (querySnapshot.size() < TAMANHO_PAGINA) {
                    semMaisDados = true
                }
            }
            .addOnFailureListener { e ->
                progressbar_paginacao.visibility = View.GONE
                carregando = false
                Snackbar.make(
                    recycler_livros,
                    "Erro ao carregar mais livros: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun IniciarComponentes() {
        recycler_livros = findViewById(R.id.recycler_livros)
        progressbar_lista = findViewById(R.id.progressbar_lista)
        progressbar_paginacao = findViewById(R.id.progressbar_paginacao)
        edit_busca_livro = findViewById(R.id.edit_busca_livro)
        bt_limpar_busca = findViewById(R.id.bt_limpar_busca)
        text_sem_resultado = findViewById(R.id.text_sem_resultado)
        bt_voltar = findViewById(R.id.bt_voltar_lista)
    }
}
