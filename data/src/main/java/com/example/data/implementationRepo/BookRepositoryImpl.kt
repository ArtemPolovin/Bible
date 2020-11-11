package com.example.data.implementationRepo

import com.example.data.mappers.MapFromBookToCellBook
import com.example.data.utils.BibleConverter
import com.example.domain.models.CellBook
import com.example.domain.models.bible.Book
import com.example.domain.repositories.IBookRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BookRepositoryImpl(
    private val bibleConverter: BibleConverter,
    private val mapper: MapFromBookToCellBook
) : IBookRepository {

    override fun getBooksList(): List<Book> {
        return bibleConverter.convertJsonToBible().Books
    }

    override fun getCellBookList(
        bookList: List<Book>,
        inputText: String,
        wordMatchPercentage: Int,
        charMatchPercentage: Int
    ): Single<List<CellBook>> {

        return Single.just(bookList)
            .subscribeOn(Schedulers.io())
            .map {
                mapper.mapFromBookListToCellBookList(
                    bookList,
                    inputText,
                    wordMatchPercentage,
                    charMatchPercentage
                )
            }
    }


}