package com.shroomlife.shliste.utils

import android.content.Context
import android.util.Log
import com.shroomlife.shliste.modules.lists.Shliste
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.io.File

object DataUtils {

    val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
    }

    suspend inline fun <reified T> loadFromStorage(
        context: Context,
        fileName: String
    ): T? = withContext(Dispatchers.IO) {
        try {
            val file = getStorageFile(context, fileName)
            if (!file.exists()) return@withContext null

            val text = file.readText()
            json.decodeFromString<T>(text)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend inline fun <reified T> saveToStorage(
        context: Context,
        data: T,
        fileName: String
    ) = withContext(Dispatchers.IO) {
        try {
            val file = getStorageFile(context, fileName)
            val text = json.encodeToString(data)
            file.writeText(text)
            Log.d("DataUtils", "Data saved to ${file.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getStorageFile(context: Context, fileName: String): File =
        File(context.filesDir, fileName)
}