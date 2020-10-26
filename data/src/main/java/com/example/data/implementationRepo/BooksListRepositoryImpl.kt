package com.example.data.implementationRepo

import com.example.data.utils.BibleConverter
import com.example.domain.models.bible.Book
import com.example.domain.repositories.BooksList

class BooksListRepositoryImpl(private val bibleConverter: BibleConverter): BooksList {

    override fun getBooksList(): List<Book> {
      return  bibleConverter.convertJsonToBible().Books
    }
}