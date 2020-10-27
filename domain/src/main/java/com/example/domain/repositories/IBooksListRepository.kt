package com.example.domain.repositories

import com.example.domain.models.bible.Book


interface IBooksListRepository {
    fun getBooksList(): List<Book>
}