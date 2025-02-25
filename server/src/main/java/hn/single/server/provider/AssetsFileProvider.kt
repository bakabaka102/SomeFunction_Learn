package hn.single.server.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import hn.single.server.Constants
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class AssetsFileProvider : ContentProvider() {

    companion object {
        private const val LIST_FILE_CODE = "1001"
        private const val LIST_FILE_BUNDLE_KEY = "LIST_FILE_BUNDLE_KEY"
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return when (uri.lastPathSegment?.substringAfterLast(".")) {
            "json" -> "application/json"
            "html" -> "text/html"
            "pem" -> "application/x-pem-file"
            "js" -> "text/javascript"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        return when (method) {
            LIST_FILE_CODE -> {
                val listFile = arrayListOf<String?>().apply { this.addAll(getListFile("", true)) }
                val bundle = Bundle().apply {
                    this.putStringArrayList(LIST_FILE_BUNDLE_KEY, listFile)
                }
                bundle
            }

            else -> null
        }
    }

    private fun getListFile(path: String, isFileNameOnly: Boolean): MutableList<String> {
        val listFile = mutableListOf<String>()
        val am = context?.assets
        am?.let {
            val files = am.list(path)
            files?.forEach {
                val tempCheckPath = if (path.isEmpty()) path else "$path/"
                when {
                    am.list(tempCheckPath + it).isNullOrEmpty() -> {
                        if (isFileNameOnly)
                            listFile.add(it)
                        else
                            listFile.add(tempCheckPath + it)
                    }

                    else -> listFile.addAll(getListFile(tempCheckPath + it, isFileNameOnly))
                }
            }
        }

        return listFile.onEach {
            println("${Constants.TAG_PROVIDER} - List file: $it")
        }
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        try {
            val assetFileName = uri.lastPathSegment?.let {
                getAssetFilePath(it)
            }.also {
                println("${Constants.TAG_PROVIDER} - Asset file name: $it")
            } ?: throw FileNotFoundException("$uri")
            return assetFileName.let {
                //context?.assets?.openNonAssetFd(it)
                context?.assets?.openFd(it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw FileNotFoundException("$uri")
        }
    }

    private fun getAssetFilePath(fileName: String): String? {
        return getListFile("", false).firstOrNull {
            it.substringAfterLast("/") == fileName
        }
    }
}