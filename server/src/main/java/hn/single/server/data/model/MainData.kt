package hn.single.server.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*data class ApiResponse(
    @SerializedName("Search")
    val dataList: List<MainData>?,
)

data class MainData(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Poster")
    val poster: String,
)*/

@Serializable
data class ApiResponse(
    @SerialName("Search")
    val dataList: List<MainData>?,
)

@Serializable
data class MainData(
    @SerialName("Title")
    val title: String,

    @SerialName("Poster")
    val poster: String,
)
