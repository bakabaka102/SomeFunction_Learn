package hn.single.server.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


class AssetProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        return true
    }

    @Throws(FileNotFoundException::class)
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor {
        val file = context?.assets?.openFd("data_custom.json")?.fileDescriptor?.toString()?.let { File(it) }
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun query(
        uri: Uri,
        projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?,
    ): Cursor {
        val cursor = MatrixCursor(arrayOf("file_content"))
        try {
            val inputStream = context?.assets?.open("data_custom.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val content = StringBuilder()
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                content.append(line).append("\n")
            }
            reader.close()
            cursor.addRow(arrayOf<Any>(content.toString()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return cursor
    }


    override fun getType(uri: Uri): String? {
        return "json"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not supported")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not supported")
    }
}

