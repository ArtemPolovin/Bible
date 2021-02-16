package com.example.domain.usecases.noteusecases

import com.example.domain.repositories.INoteRepository

class RemoveTopicUseCase(private val noteRepository: INoteRepository) {
    operator fun invoke(topicId: Long) = noteRepository.removeTopic(topicId)
}