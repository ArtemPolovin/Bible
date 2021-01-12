package com.example.data.utils

import android.content.SharedPreferences

class BibleDataCache(private val sharedPreferences: SharedPreferences) {

    fun saveBookId(bookId: Int) {
        sharedPreferences.edit().putInt(BOOK_ID_KEY,bookId).apply()
    }

    fun saveChapterId(chapterId: Int) {
        sharedPreferences.edit().putInt(CHAPTER_ID_KEY, chapterId).apply()
    }

    fun loadBookId(): Int {
       return sharedPreferences.getInt(BOOK_ID_KEY, DEFAULT_VALUE_BOOK_ID)
    }

    fun loadChapterId(): Int {
        return sharedPreferences.getInt(CHAPTER_ID_KEY, DEFAULT_VALUE_CHAPTER_ID)
    }
}