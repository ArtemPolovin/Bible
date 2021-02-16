package com.example.domain.usecases.noteusecases

import com.example.domain.repositories.INoteRepository

class InsertNoteUseCase(private val noteRepository: INoteRepository) {
    operator fun invoke(note: String, topicId: Long, noteTitle: String, id: Int) =
        noteRepository.insertNote(note,topicId,noteTitle,id)
}