package com.example.bible.utils

import com.example.domain.models.CellBook
import com.example.domain.models.bible.Book

sealed class BookViewState {

    object Loading : BookViewState()

    object Error : BookViewState()

    data class BooksLoaded(val booksList: List<Book>): BookViewState()
    data class CellBooksLoaded(val cellBookList: List<CellBook>): BookViewState()

}