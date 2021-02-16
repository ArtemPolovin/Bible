package com.example.data.implementationRepo

import android.util.Log
import com.example.data.db.dao.INoteDao
import com.example.data.mappers.NoteMapper
import com.example.domain.models.NoteModel
import com.example.domain.models.TopicModel
import com.example.domain.repositories.INoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single

class NoteRepositoryImpl(
    private val noteDao: INoteDao,
    private val mapper: NoteMapper,
    private val schedulersIO: Scheduler
) : INoteRepository {

    override fun getNotesByTopicId(topicId: Long): Flowable<List<NoteModel>> {
        return noteDao.getNotesByTopicId(topicId)
            .subscribeOn(schedulersIO).
                map { mapper.mapFromNoteEntityToNoteModelList(it) }
    }

    override fun getTopics(): Flowable<List<TopicModel>> {
        return noteDao.getTopics()
            .subscribeOn(schedulersIO)
            .map { mapper.mapFromTopicEntityToTopicModelList(it) }
    }

    override fun insertNote(note: String, topicId: Long, noteTitle: String, id: Int): Completable {
        return Completable.fromCallable {
            noteDao.insertNote(mapper.mapToNoteEntity(note, topicId,noteTitle, id))
        }
            .subscribeOn(schedulersIO)
    }

    override fun insertTopic(topic: String): Completable {
        return Completable.fromCallable {
            noteDao.insertTopic(mapper.mapToTopicEntity(topic))
        }
            .subscribeOn(schedulersIO)
    }

    override fun removeTopic(topicId: Long): Completable {
        return Completable.fromCallable {
            noteDao.removeTopic(topicId)
        }
            .subscribeOn(schedulersIO)
    }

    override fun removeNote(noteId: Long): Completable {
        return Completable.fromCallable {
            noteDao.removeNote(noteId)
        }
            .subscribeOn(schedulersIO)
    }

}