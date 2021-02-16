package com.example.domain.usecases.noteusecases

import com.example.domain.repositories.INoteRepository

class InsertTopicToDBUseCase(private val noteRepository: INoteRepository) {
   operator fun invoke(topic: String) = noteRepository.insertTopic(topic)
}