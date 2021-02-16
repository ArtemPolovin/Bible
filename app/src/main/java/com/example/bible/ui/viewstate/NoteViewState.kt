package com.example.bible.ui.viewstate

import com.example.domain.models.NoteModel
import com.example.domain.models.TopicModel

sealed class NoteViewState {
    object Loading : NoteViewState()

    object Error : NoteViewState()

    data class TopicsLoaded(val topicsList: List<TopicModel>): NoteViewState()
    data class NotesLoaded(val notesList: List<NoteModel>): NoteViewState()
}