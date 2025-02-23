package com.app.func.view.chart.models

//import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.app.func.view.chart.utils.TelemetryParsingConstant
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
    @SerializedName(TelemetryParsingConstant.PURITY_WATER_IN)
    val waterIn: List<ConsumeData>?,
    @SerializedName(TelemetryParsingConstant.PURITY_WATER_OUT)
    val waterOut: List<ConsumeData>?,
    @SerializedName(TelemetryParsingConstant.FAN_TEMPERATURE)
    val consumeDataFan: List<ConsumeData>?
) : Parcelable


@Parcelize
data class ReportWaterResponse(
    @SerializedName(TelemetryParsingConstant.PURITY_WATER_IN)
    val waterIn: List<ConsumeData>?,
    @SerializedName(TelemetryParsingConstant.PURITY_WATER_OUT)
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
        val comparatorSortDate: Comparator<ConsumeData> = kotlin.Comparator { o1, o2 ->
            (o1.ts - o2.ts).toInt()
        }
    }
}