package com.example.bible.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetBooksListUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class BookFactory @Inject constructor(
    private val getBooksListUseCase: GetBooksListUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(getBooksListUseCase) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}