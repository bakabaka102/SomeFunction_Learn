package hn.single.server.ui.loadmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.android.material.R
import hn.single.server.data.model.MainData
import hn.single.server.databinding.ItemMainBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = ArrayList<MainData>()

    fun setItems(items: List<MainData>) {
        val diffResult = DiffUtil.calculateDiff(MainDiffCallBack(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainViewHolder(private val binding: ItemMainBinding) : ViewHolder(binding.root) {

        fun bind(mainData: MainData) {
            binding.tvItem.text = mainData.title
            Glide.with(itemView.context)
                .load(mainData.poster)
                .placeholder(R.color.design_default_color_secondary)
                .into(binding.ivItem)
        }

    }
}

class MainDiffCallBack(private val oldList: List<MainData>, private val newList: List<MainData>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].title == newList[newItemPosition].title)

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}