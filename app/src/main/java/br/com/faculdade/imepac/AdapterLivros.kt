package br.com.faculdade.imepac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class que representa um Livro na lista
data class Livro(
    val id: String = "",
    val titulo: String = "",
    val autor: String = "",
    val ano: String = ""
)

class AdapterLivros(
    private val listaLivros: MutableList<Livro>,
    private val onItemClick: (Livro) -> Unit
) : RecyclerView.Adapter<AdapterLivros.LivroViewHolder>() {

    inner class LivroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitulo: TextView = itemView.findViewById(R.id.text_titulo_livro)
        val textAutor: TextView = itemView.findViewById(R.id.text_autor_livro)
        val textAno: TextView = itemView.findViewById(R.id.text_ano_livro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro, parent, false)
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        holder.textTitulo.text = livro.titulo
        holder.textAutor.text = "Autor: ${livro.autor}"
        holder.textAno.text = "Ano: ${livro.ano}"

        holder.itemView.setOnClickListener {
            onItemClick(livro)
        }
    }

    override fun getItemCount(): Int = listaLivros.size

    // Adiciona novos itens ao final da lista (usado pela paginação)
    fun adicionarItens(novosItens: List<Livro>) {
        val posicaoInicial = listaLivros.size
        listaLivros.addAll(novosItens)
        notifyItemRangeInserted(posicaoInicial, novosItens.size)
    }
}
