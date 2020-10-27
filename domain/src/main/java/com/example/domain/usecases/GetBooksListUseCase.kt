package com.example.domain.usecases

import com.example.domain.models.bible.Book
import com.example.domain.repositories.IBooksListRepository

class GetBooksListUseCase(private val IBooksListRepository: IBooksListRepository) {
    operator fun invoke(): List<Book> = IBooksListRepository.getBooksList()
}