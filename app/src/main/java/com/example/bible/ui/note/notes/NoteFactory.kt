package com.example.bible.ui.note.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.noteusecases.FetchNotesUseCase
import com.example.domain.usecases.noteusecases.RemoveNoteUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NoteFactory @Inject constructor(
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(fetchNotesUseCase, removeNoteUseCase) as T
        }
        throw IllegalArgumentException("Error! The ViewModel was not found")
    }
}