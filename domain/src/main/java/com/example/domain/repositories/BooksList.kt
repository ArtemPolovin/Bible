package com.example.domain.repositories

import com.example.domain.models.bible.Book


interface BooksList {
    fun getBooksList(): List<Book>
}