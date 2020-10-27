package com.example.data.implementationRepo

import com.example.data.utils.BibleConverter
import com.example.domain.models.bible.Book
import com.example.domain.repositories.IBooksListRepository

class BooksListRepositoryImpl(private val bibleConverter: BibleConverter): IBooksListRepository {

    override fun getBooksList(): List<Book> {
      return  bibleConverter.convertJsonToBible().Books
    }
}