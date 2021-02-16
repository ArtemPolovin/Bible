package com.example.bible.ui.note.notepaage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.noteusecases.InsertNoteUseCase
import com.example.domain.usecases.noteusecases.RemoveNoteUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class WriteNoteFactory @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteNoteViewModel::class.java)) {
            return WriteNoteViewModel(insertNoteUseCase) as T
        }
        throw IllegalArgumentException("Error! The ViewModel was not found")
    }
}