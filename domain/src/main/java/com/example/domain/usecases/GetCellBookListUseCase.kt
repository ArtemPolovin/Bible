package com.example.domain.usecases

import com.example.domain.models.CellBook
import com.example.domain.models.bible.Book
import com.example.domain.repositories.IBookRepository
import io.reactivex.Single

class GetCellBookListUseCase(private val bookRepo: IBookRepository) {
    fun invoke(
        bookList: List<Book>,
        inputText: String,
        wordMatchPercentage: Int,
        charMatchPercentage: Int
    ): Single<List<CellBook>> =
        bookRepo.getCellBookList(bookList, inputText, wordMatchPercentage, charMatchPercentage)
}
