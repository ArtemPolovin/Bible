package com.example.bible.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bible.utils.BookViewState
import com.example.domain.models.bible.Book
import com.example.domain.usecases.GetBooksListUseCase

class BookViewModel(private val getBooksListUseCase: GetBooksListUseCase) : ViewModel() {

    private val _booksViewState = MutableLiveData<BookViewState>()
    val bookViewState: LiveData<BookViewState> get() = _booksViewState

    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> get() = _filteredBooks

    init {
        getBooksList()
    }

    private fun getBooksList() {
        _booksViewState.value = BookViewState.Loading
        _booksViewState.value = BookViewState.BooksLoaded(getBooksListUseCase())
    }

    fun bookSearch(inputResult: String) {
        _filteredBooks.value = getBooksListUseCase().filter {
            it.BookName.contains(inputResult, true)
        }
    }

    fun refreshBooksList() {
        getBooksList()
    }
}