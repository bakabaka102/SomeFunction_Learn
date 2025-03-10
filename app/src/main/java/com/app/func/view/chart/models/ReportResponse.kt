package com.app.func.view.chart.models

//import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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