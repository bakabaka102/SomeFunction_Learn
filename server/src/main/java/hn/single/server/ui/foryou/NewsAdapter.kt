package hn.single.server.ui.foryou

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hn.single.server.R
import hn.single.server.ui.search.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSource: TextView = itemView.findViewById(R.id.tvSource)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPublishedAt: TextView = itemView.findViewById(R.id.tvPublishedAt)
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)

        fun bind(article: Article) {
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvPublishedAt.text = formatDate(article.publishedAt)

            Glide.with(itemView)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_downloading)
                .centerCrop()
                .into(ivThumbnail)
        }

        private fun formatDate(dateString: String): String {
            return try {
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                val output = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
                val date = input.parse(dateString)
                if (date != null) output.format(date) else ""
            } catch (e: Exception) {
                ""
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}
