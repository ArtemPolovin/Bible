package com.example.data.utils

import android.content.Context
import android.util.Log
import com.example.data.R
import com.example.domain.models.bible.Bible
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class BibleConverter(private val gson: Gson, private val context: Context ) {

    private fun fromJsonToString(): String {
        val inputStream = context.resources.openRawResource(R.raw.bible)
        val isReader = InputStreamReader(inputStream)
        val reader = BufferedReader(isReader)
        val strBuilder = StringBuilder()
        reader.use {
            var str = it.readLine()
            while (str != null) {
                strBuilder.append(str)
                str = it.readLine()
            }
        }
        inputStream.close()
        isReader.close()
        return strBuilder.toString()
    }

    fun convertJsonToBible(): Bible {
        return gson.fromJson(fromJsonToString(),Bible::class.java)
    }
}