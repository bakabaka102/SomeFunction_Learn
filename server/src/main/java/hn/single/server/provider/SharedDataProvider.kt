package hn.single.server.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.net.toUri
import hn.single.server.R
import java.io.File
import java.io.FileOutputStream

class SharedDataProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "hn.single.server.sharedprovider"
        val URI_STRINGS: Uri = "content://$AUTHORITY/strings".toUri()
        val URI_IMAGE: Uri = "content://$AUTHORITY/image".toUri()
        val URI_IMAGE_LIST: Uri = "content://$AUTHORITY/image_list".toUri()
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: MatrixCursor

        return when (uri) {
            URI_STRINGS -> {
                cursor = MatrixCursor(arrayOf("key", "value")).apply {
                    addRow(arrayOf("title", "Ảnh đẹp mùa xuân"))
                    addRow(arrayOf("description", "Tuyển tập ảnh phong cảnh tuyệt đẹp"))
                }
                cursor
            }

            URI_IMAGE_LIST -> {
                cursor = MatrixCursor(arrayOf("name", "uri")).apply {
                    /*addRow(arrayOf("Apple", "$URI_IMAGE?id=ic_apple"))
                    addRow(arrayOf("Discord", "$URI_IMAGE?id=ic_discord"))
                    addRow(arrayOf("Facebook", "$URI_IMAGE?id=ic_facebook"))*/
                    addRow(arrayOf("Mouse", "$URI_IMAGE?id=mouse"))
                }
                cursor
            }

            else -> null
        }
    }

    /*override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val context = context ?: return null
        val id = uri.getQueryParameter("id") ?: return null

        val resId = when (id) {
            "mouse" -> R.raw.mouse
            else -> return null
        }

        return try {
            context.resources.openRawResourceFd(resId)?.let { assetFd ->
                Log.d("SharedProvider", "Returning descriptor for: $id")
                assetFd.parcelFileDescriptor
            }
        } catch (e: Exception) {
            Log.e("SharedProvider", "Error opening raw image: ${e.message}")
            null
        }
    }*/
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val context = context ?: return null
        val id = uri.getQueryParameter("id") ?: return null

        val resId = when (id) {
            "mouse" -> R.raw.mouse
            else -> return null
        }

        return try {
            val inputStream = context.resources.openRawResource(resId)
            val tempFile = File(context.cacheDir, "$id.png")
            val outputStream = FileOutputStream(tempFile)

            inputStream.use { ins ->
                outputStream.use { outs ->
                    ins.copyTo(outs)
                }
            }

            ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
        } catch (e: Exception) {
            Log.e("SharedProvider", "Error copying file: ${e.message}")
            null
        }
    }

    // Các method không dùng
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
}
