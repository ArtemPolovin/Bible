package com.example.bible.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetCellHomeListUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeFactory @Inject constructor(
    private val getCellHomeListUseCase: GetCellHomeListUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getCellHomeListUseCase) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}