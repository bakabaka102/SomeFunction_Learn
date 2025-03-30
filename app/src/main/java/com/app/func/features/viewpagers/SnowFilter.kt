package com.app.func.features.viewpagers

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import java.util.Random

object SnowFilter {
    private var COLOR_MAX = 0xff

    fun applySnowEffect(source: Bitmap): Bitmap {
        // get image size
        val width = source.width
        val height = source.height
        val pixels = IntArray(width * height)
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height)
        // random object
        val random = Random()
        var threshHold: Int
        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {
                // get current index in 2D-matrix
                val index = y * width + x
                // get color
                val red = Color.red(pixels[index])
                val green = Color.green(pixels[index])
                val black = Color.blue(pixels[index])
                // generate threshold
                threshHold = random.nextInt(COLOR_MAX)
                if (red > threshHold && green > threshHold && black > threshHold) {
                    pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX)
                }
            }
        }
        // output bitmap
        val bitmapOut = createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmapOut
    }
}