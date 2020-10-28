package com.example.bible.ui.readbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.bible.Book
import com.example.domain.usecases.GetBooksListUseCase

class ReadingPageViewModel(private val getBooksListUseCase: GetBooksListUseCase) : ViewModel() {

    private val _chapter = MutableLiveData<String>()
    val chapter: LiveData<String> get() = _chapter

    private fun getBookById(bookId: Int): Book {
        return getBooksListUseCase().filter { it.BookId == bookId }[0]
    }

    fun getChapter(bookId: Int) {
        val chapterBuilder = StringBuilder()

       getBookById(bookId).Chapters[0].Verses.forEach {
           chapterBuilder.append("  ${it.VerseId}. ${it.Text}").append("\n")
       }

        _chapter.value = chapterBuilder.toString()
    }
}