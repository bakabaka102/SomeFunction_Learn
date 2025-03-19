package com.app.func.view.chart.models

//import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

fun reportResponse(): ReportResponse {
    return ReportResponse(
        listOf(
            ConsumeData(10L, "123"),
            ConsumeData(13L, "98"),
            ConsumeData(20L, "116"),
            ConsumeData(16L, "87"),
            ConsumeData(2L, "78"),
            ConsumeData(8L, "80"),
            ConsumeData(1L, "100")
        ),
        listOf(
            ConsumeData(10L, "140"),
            ConsumeData(8L, "99"),
            ConsumeData(21L, "66"),
            ConsumeData(14L, "56"),
            ConsumeData(11L, "98"),
            ConsumeData(6L, "52"),
            ConsumeData(2L, "78"),
            ConsumeData(4L, "123"),
            ConsumeData(16L, "25")
        ),
    )
}

@Parcelize
data class ReportResponse(
    @SerializedName("power")
    val power: List<ConsumeData>,
    @SerializedName("freezerTemperature")
    val consumeData: List<ConsumeData>,
) : Parcelable

@Parcelize
data class ReportData(
    @SerializedName("power")
    val power: List<ConsumeData>?,
    @SerializedName("freezerTemperature")
    val consumeData: List<ConsumeData>,
    @SerializedName("purityWaterIn")
    val waterIn: List<ConsumeData>?,
    @SerializedName("purityWaterOut")
    val waterOut: List<ConsumeData>?,
    @SerializedName("temperature")
    val consumeDataFan: List<ConsumeData>?
) : Parcelable


@Parcelize
data class ReportWaterResponse(
    @SerializedName("purityWaterIn")
    val waterIn: List<ConsumeData>?,
    @SerializedName("purityWaterOut")
    val waterOut: List<ConsumeData>?,
) : Parcelable

@Parcelize
data class ConsumeData(
    @SerializedName("ts")
    var ts: Long,
    @SerializedName("value")
    var value: String
) : Parcelable {
    companion object {
        val comparatorSortDate: Comparator<ConsumeData> = Comparator { o1, o2 ->
            (o1.ts - o2.ts).toInt()
        }
    }
}