package ru.soulsidejourney.dictionary.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.soulsidejourney.dictionary.R

class DictionaryAdapter(Context : Context, private val dictionary: List<Pair>) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder> () {
    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_pair, parent, false))
    }

    //извлекает данные и использует для заполнения разметки
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //связываем данные, получаем картинку и текст
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = dictionary.size

    //позиция элемента в списке
    private fun getItem(position: Int) : Pair = dictionary[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstWord: TextView = itemView.findViewById(R.id.first_word)
        private val secondWord: TextView = itemView.findViewById(R.id.second_word)

        fun bind(pair: Pair) {
            firstWord.text = pair.firstWord
            secondWord.text = pair.secondWord
        }
    }
}