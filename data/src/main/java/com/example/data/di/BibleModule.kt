package com.example.data.di

import android.content.Context
import com.example.data.implementationRepo.BooksListRepositoryImpl
import com.example.data.utils.BibleConverter
import com.example.domain.repositories.BooksList
import com.example.domain.usecases.GetBooksListUseCase
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
    @Singleton
    fun provideBooksListRepoImpl (booksConverter: BibleConverter): BooksList =
        BooksListRepositoryImpl(booksConverter)

    @Provides
    fun provideGetBooksListUseCase(booksList: BooksList) = GetBooksListUseCase(booksList)

}