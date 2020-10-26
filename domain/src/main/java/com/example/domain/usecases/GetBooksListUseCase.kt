package com.example.domain.usecases

import com.example.domain.models.bible.Book
import com.example.domain.repositories.BooksList

class GetBooksListUseCase(private val booksList: BooksList) {
    operator fun invoke(): List<Book> = booksList.getBooksList()
}