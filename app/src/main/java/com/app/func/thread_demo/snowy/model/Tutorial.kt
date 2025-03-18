package com.app.func.thread_demo.snowy.model

import android.content.Context
import android.os.Parcelable
import com.app.func.R
//import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tutorial(
    val name: String,
    val url: String,
    val description: String
) : Parcelable

fun getTutorialData(context: Context?): List<Tutorial> {
    val tutorialList = arrayListOf<Tutorial>()
    tutorialList.add(
        Tutorial(
            context?.getString(R.string.kotlin_title) ?: "",
            context?.getString(R.string.kotlin_url) ?: "",
            context?.getString(R.string.kotlin_desc) ?: "",
        )
    )
    tutorialList.add(
        Tutorial(
            context?.getString(R.string.android_name) ?: "",
            context?.getString(R.string.android_url) ?: "",
            context?.getString(R.string.android_desc) ?: "",
        )
    )
    tutorialList.add(
        Tutorial(
            context?.getString(R.string.rxkotlin_name) ?: "",
            context?.getString(R.string.rxkotlin_url) ?: "",
            context?.getString(R.string.rxkotlin_desc) ?: "",
        )
    )
    tutorialList.add(
        Tutorial(
            context?.getString(R.string.kitura_name) ?: "",
            context?.getString(R.string.kitura_url) ?: "",
            context?.getString(R.string.kitura_desc) ?: "",
        )
    )
    return tutorialList
}