package com.app.func.io

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

suspend fun saveFileToDisk(
    context: Context,
    responseBody: ResponseBody,
    fileName: String,
): File? {
    return withContext(Dispatchers.IO) {
        try {
            // Create file in app-specific storage
            val file = File(context.getExternalFilesDir(null), fileName)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                inputStream = responseBody.byteStream()
                outputStream = FileOutputStream(file)

                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.flush()
                file
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

suspend fun saveJsonToFile(
    context: Context,
    fileName: String,
    responseBody: ResponseBody
): File? {
    return withContext(Dispatchers.IO) {
        try {
            // Tạo hoặc mở file JSON
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            outputStream.use { stream ->
                stream.write(responseBody.bytes()) // Ghi nội dung vào file
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

private fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}
