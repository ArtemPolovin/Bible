package com.example.domain.usecases

import com.example.domain.models.bible.Book
import com.example.domain.repositories.IBookRepository

class GetBooksListUseCase(private val IBookRepository: IBookRepository) {
    operator fun invoke(): List<Book> = IBookRepository.getBooksList()
}