package hn.single.server.ui.foryou

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import hn.single.server.R
import hn.single.server.databinding.ItemNewsBinding
import hn.single.server.ui.search.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(getItem(position))
        }
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentTarget: CustomTarget<Drawable>? = null
        /*fun bind(article: Article) {
            binding.tvSource.text = article.source.name
            binding.tvTitle.text = article.title
            binding.tvPublishedAt.text = formatDate(article.publishedAt)

            Glide.with(binding.root)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_placeholder_rectangle)
                .error(R.drawable.ic_placeholder_rectangle) // fallback nếu lỗi
                .centerCrop()
                .into(binding.ivThumbnail)
        }*/

        fun bind(article: Article) {
            binding.tvSource.text = article.source.name
            binding.tvTitle.text = article.title
            binding.tvPublishedAt.text = formatDate(article.publishedAt)

            // Reset view state
            binding.imageLoading.isVisible = true
            binding.ivThumbnail.setImageDrawable(null)
            binding.ivThumbnail.isGone = true

            // Cancel previous Glide request if needed
            currentTarget?.let {
                Glide.with(binding.root).clear(it)
            }

            // New Glide target
            currentTarget = object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.ivThumbnail.setImageDrawable(resource)
                    binding.imageLoading.isGone = true
                    binding.ivThumbnail.isVisible = true
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.imageLoading.isGone = true
                    binding.ivThumbnail.isGone = true
                }
            }

            Glide.with(binding.root)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_placeholder_rectangle)
                .error(R.drawable.ic_placeholder_rectangle)
                .centerCrop()
                .into(currentTarget!!)
        }

        private fun formatDate(dateString: String): String {
            return try {
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                val output = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
                val date = input.parse(dateString)
                date?.let { output.format(it) } ?: ""
            } catch (e: Exception) {
                e.message
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
