package com.example.bible.utils

import com.example.domain.models.bible.Book

sealed class BookViewState {

    object Loading : BookViewState()

    object Error : BookViewState()

    data class BooksLoaded(val booksList: List<Book>): BookViewState()

}