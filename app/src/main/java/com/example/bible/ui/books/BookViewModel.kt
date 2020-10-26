package com.example.bible.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bible.utils.BookViewState
import com.example.domain.usecases.GetBooksListUseCase

class BookViewModel(private val getBooksListUseCase: GetBooksListUseCase) : ViewModel() {

    private val _booksViewState = MutableLiveData<BookViewState>()
    val bookViewState : LiveData<BookViewState> get() = _booksViewState

    init {
        getBooksList()
    }

    private fun getBooksList() {
        _booksViewState.value = BookViewState.Loading
       _booksViewState.value = BookViewState.BooksLoaded(getBooksListUseCase())
    }

    fun refreshBooksList() {
        getBooksList()
    }
}