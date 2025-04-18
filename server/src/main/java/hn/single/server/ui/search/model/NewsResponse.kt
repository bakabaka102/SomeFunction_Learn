package hn.single.server.ui.search.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
)

@Serializable
@Keep
@Parcelize
data class Article(
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null,
): Parcelable

@Serializable
@Keep
@Parcelize
data class Source(
    val id: String? = null,
    val name: String,
): Parcelable