package com.example.bible.ui.readbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.utils.BibleDataCache
import com.example.domain.usecases.GetBooksListUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ReadingPageFactory @Inject constructor(
    private val getBooksListUseCase: GetBooksListUseCase,
    private val bibleDataCache: BibleDataCache
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadingPageViewModel::class.java)) {
            return ReadingPageViewModel(getBooksListUseCase,bibleDataCache) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}