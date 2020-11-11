package com.example.domain.repositories

import com.example.domain.models.CellBook
import com.example.domain.models.bible.Book
import io.reactivex.Single


interface IBookRepository {
    fun getBooksList(): List<Book>
    fun getCellBookList(
        bookList: List<Book>,
        inputText: String,
        wordMatchPercentage: Int,
        charMatchPercentage: Int
    ): Single<List<CellBook>>
}