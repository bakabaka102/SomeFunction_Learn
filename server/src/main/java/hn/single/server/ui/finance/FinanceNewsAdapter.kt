package hn.single.server.ui.finance

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

class FinanceNewsAdapter: ListAdapter<Article, FinanceNewsAdapter.FinanceNewsViewHolder>(DiffCallback()) {

    var onItemClick: ((Article) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceNewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinanceNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinanceNewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(article)
        }
    }

    inner class FinanceNewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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

            // Show loading animation
            binding.imageLoading.isVisible = true
            binding.ivThumbnail.isVisible = true

            Glide.with(binding.root)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_placeholder_rectangle) // optional fallback
                .error(R.drawable.ic_placeholder_rectangle) // fallback nếu lỗi
                .centerCrop()
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.ivThumbnail.setImageDrawable(resource)
                        binding.imageLoading.isGone = true
                        binding.ivThumbnail.isVisible = true
                    }

                    // Glide gọi hàm này khi:
                    //View bị tách khỏi màn hình (ví dụ scroll quá xa trong RecyclerView).
                    //Hoặc khi đang cancel tải ảnh (do view bị recycled).
                    //có thể dùng nó để clear ảnh, đặt lại placeholder, hoặc reset view
                    override fun onLoadCleared(placeholder: Drawable?) {
                        // no-op: Optional: clear hoặc gán placeholder
                        binding.imageLoading.isGone = true
                    }
                })
        }

        private fun formatDate(dateString: String): String {
            return try {
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                val output = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
                val date = input.parse(dateString)
                if (date != null) output.format(date) else ""
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
