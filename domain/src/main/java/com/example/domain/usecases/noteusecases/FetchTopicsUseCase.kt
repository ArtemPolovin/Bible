package com.example.domain.usecases.noteusecases

import com.example.domain.repositories.INoteRepository

class FetchTopicsUseCase(private val noteRepository: INoteRepository) {
  operator fun invoke() = noteRepository.getTopics()
}