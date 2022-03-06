package com.app.func.rx_function.utils

import android.util.Log
import com.app.func.BuildConfig

object Logger {

    fun logD(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
}