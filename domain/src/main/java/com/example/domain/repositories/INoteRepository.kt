package com.example.domain.repositories

import com.example.domain.models.NoteModel
import com.example.domain.models.TopicModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface INoteRepository {
    fun getNotesByTopicId(topicId: Long): Flowable<List<NoteModel>>
    fun getTopics(): Flowable<List<TopicModel>>
    fun insertNote(note: String, topicId: Long, noteTitle: String, id: Int): Completable
    fun insertTopic(topic: String): Completable
    fun removeTopic(topicId: Long): Completable
    fun removeNote(noteId: Long): Completable

}