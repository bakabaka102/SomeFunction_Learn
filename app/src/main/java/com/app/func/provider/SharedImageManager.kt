package com.app.func.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.app.func.utils.Constants

object SharedImageManager {
    private val imageCache = mutableMapOf<String, Bitmap>()

    fun getImage(context: Context, name: String): Bitmap? {
        imageCache[name]?.let { return it }

        val uri = "content://${Constants.PKG_RESOURCE_APP}.sharedprovider/image?id=$name".toUri()
        return try {
            context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)?.also {
                    imageCache[name] = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
