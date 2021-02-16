package com.example.domain.usecases.noteusecases

import com.example.domain.repositories.INoteRepository

class RemoveNoteUseCase(private val noteRepository: INoteRepository) {
    operator fun invoke(noteId: Long) = noteRepository.removeNote(noteId)
}