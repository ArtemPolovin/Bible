package com.example.data.di

import android.content.Context
import com.example.data.implementationRepo.BookRepositoryImpl
import com.example.data.mappers.MapFromBookToCellBook
import com.example.data.utils.BibleConverter
import com.example.domain.repositories.IBookRepository
import com.example.domain.usecases.GetBooksListUseCase
import com.example.domain.usecases.GetCellBookListUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BibleModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    fun provideBibleConverter(gson: Gson) = BibleConverter(gson, context)

    @Provides
    fun provideMapFromBookListToCellBookList() = MapFromBookToCellBook()

    @Provides
    @Singleton
    fun provideBooksListRepoImpl (booksConverter: BibleConverter,mapper: MapFromBookToCellBook): IBookRepository =
        BookRepositoryImpl(booksConverter,mapper)

    @Provides
    fun provideGetBooksListUseCase(bookRepository: IBookRepository) = GetBooksListUseCase(bookRepository)

    @Provides
    fun provideGetCellBookListUseCase(bookRepository: IBookRepository) = GetCellBookListUseCase(bookRepository)

}