package com.example.bible.ui.note.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.noteusecases.FetchTopicsUseCase
import com.example.domain.usecases.noteusecases.InsertTopicToDBUseCase
import com.example.domain.usecases.noteusecases.RemoveTopicUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TopicFactory @Inject constructor(
    private val fetchTopicsUseCase: FetchTopicsUseCase,
    private val insertTopicToDBUseCase: InsertTopicToDBUseCase,
    private val removeTopicUseCase: RemoveTopicUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicViewModel::class.java)) {
            return TopicViewModel(fetchTopicsUseCase, insertTopicToDBUseCase,removeTopicUseCase) as T
        }
        throw IllegalArgumentException("Error! The ViewModel was not found")
    }
}