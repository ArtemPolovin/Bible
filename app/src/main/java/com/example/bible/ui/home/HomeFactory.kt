package com.example.bible.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.utils.BibleDataCache
import com.example.domain.usecases.GetCellHomeListUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeFactory @Inject constructor(
    private val getCellHomeListUseCase: GetCellHomeListUseCase,
    private val bibleDataCache: BibleDataCache
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getCellHomeListUseCase,bibleDataCache) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}