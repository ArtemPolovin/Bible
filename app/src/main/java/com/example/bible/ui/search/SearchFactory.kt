package com.example.bible.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetBooksListUseCase
import com.example.domain.usecases.GetCellBookListUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchFactory @Inject constructor(
    private val getBooksListUseCase: GetBooksListUseCase,
    private val getCellBookListUseCase: GetCellBookListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(getBooksListUseCase,getCellBookListUseCase) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}