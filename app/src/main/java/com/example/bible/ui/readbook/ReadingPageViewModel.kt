package com.example.bible.ui.readbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.bible.Book
import com.example.domain.models.bible.Chapter
import com.example.domain.usecases.GetBooksListUseCase

class ReadingPageViewModel(private val getBooksListUseCase: GetBooksListUseCase) : ViewModel() {

    private var chapterNumber = 0
    private var bookId = 0
    private var listOfChapters = listOf<Chapter>()

    private val _chapter = MutableLiveData<String>()
    val chapter: LiveData<String> get() = _chapter

    private val _numberOfChapter = MutableLiveData<Int>()
    val numberOfChapter: LiveData<Int> get() = _numberOfChapter

    private val _lastChapterNumber = MutableLiveData<Int>()
    val lastChapterNumber: LiveData<Int> get() = _lastChapterNumber

    private val _bookName = MutableLiveData<String>()
    val bookName: LiveData<String> get() = _bookName

    private val _chaptersNumbers = MutableLiveData<List<Int>>()
    val chapterNumbers: LiveData<List<Int>> get() = _chaptersNumbers

    fun receiveBookData(numberOfBookId: Int, numberOfChapter: Int ) {
        chapterNumber = numberOfChapter
        bookId = numberOfBookId
        buildChapter(chapterNumber)
        getChapterList()
    }

    private fun getBookById(): Book {
        val book = getBooksListUseCase().filter { it.BookId == bookId }[0]
        _bookName.value = book.BookName
        return book
    }

    private fun buildChapter(chapter: Int) {
        val chapterBuilder = StringBuilder()
        listOfChapters = getBookById().Chapters

        listOfChapters[chapter - 1].Verses.forEach {
            chapterBuilder.append("  ${it.VerseId}. ${it.Text}").append("\n")
        }
        _chapter.value = chapterBuilder.toString()
        _numberOfChapter.value = chapter
        _lastChapterNumber.value = listOfChapters.size
    }

    fun onClickButtonSwitchPage(isNextPage: Boolean) {
        if (isNextPage) {
            chapterNumber += 1
            buildChapter(chapterNumber)
        } else {
            chapterNumber -= 1
            buildChapter(chapterNumber)
        }
    }

    private fun getChapterList() {
        val listOfChapterNumbers = mutableListOf<Int>()
        listOfChapters.forEach {
            listOfChapterNumbers.add(it.ChapterId)
        }
        _chaptersNumbers.value = listOfChapterNumbers
    }

    fun chosenChapter(numberOfChapter: Int) {
        chapterNumber = numberOfChapter
        buildChapter(chapterNumber)
    }
}