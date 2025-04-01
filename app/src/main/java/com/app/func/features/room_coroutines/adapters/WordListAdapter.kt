package com.app.func.features.room_coroutines.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.app.func.databinding.ItemNoteBinding
import com.app.func.features.room_coroutines.Word

/*private val diffCallback = object : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Word, newItem: Word): Any? {
        return oldItem == newItem
    }
}

class WordListAdapter(private val onItemClickListener: (Word) -> Unit) :
    ListAdapter<Word, WordListAdapter.WordHolder>(diffCallback) {

    inner class WordHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindDataToViews(word: Word) {
            binding.textId.text = word.id.toString()
            binding.textContent.text = word.word
        }

        init {
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return WordHolder(binding)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        with(getItem(position)) {
            holder.bindDataToViews(this)
        }
    }
}*/

class WordListAdapter(
    private val onItemClickListener: (Word) -> Unit,
) : RecyclerView.Adapter<WordListAdapter.WordHolder>() {

    var onItemRemove: ((Word) -> Unit)? = null

    var words: MutableList<Word> = mutableListOf()
    private var recentlyDeletedItem: Word? = null
    private var recentlyDeletedItemPosition: Int = -1

    inner class WordHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindDataToViews(word: Word) {
            binding.textId.text = word.id.toString()
            binding.textContent.text = word.word
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION) {
                    onItemClickListener(words[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return WordHolder(binding)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bindDataToViews(words[position])
    }

    override fun getItemCount(): Int = words.size

    fun submitList(newList: List<Word>) {
        /*val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = wordList.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return wordList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return wordList[oldItemPosition] == newList[newItemPosition]
            }
        })*/
        /* wordList.clear()
         wordList.addAll(newList)*/
        words = newList.toMutableList()
        notifyDataSetChanged()
        //diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        recentlyDeletedItem = words[position]
        recentlyDeletedItemPosition = position
        words.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem() {
        recentlyDeletedItem?.let {
            words.add(recentlyDeletedItemPosition, it)
            notifyItemInserted(recentlyDeletedItemPosition)
        }
    }

    fun removeWord(word: Word) {
        //wordList.removeAt(position)
        onItemRemove?.invoke(word)
        //notifyItemRemoved(position)
    }
}
