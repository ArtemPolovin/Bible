package com.example.data.mappers

import com.example.data.db.tables.NoteEntity
import com.example.data.db.tables.TopicEntity
import com.example.domain.models.NoteModel
import com.example.domain.models.TopicModel

class NoteMapper {
    fun mapToNoteEntity(note:String, topicId: Long, noteTitle: String, id: Int): NoteEntity {
        return NoteEntity(
            id = id,
            note = note,
            topic_id = topicId,
            noteId = System.currentTimeMillis(),
            noteTitle = noteTitle
        )
    }

    fun mapToTopicEntity(topic: String): TopicEntity {
        return TopicEntity(topic = topic, topicId = System.currentTimeMillis())
    }

    fun mapFromNoteEntityToNoteModelList(noteEntityList: List<NoteEntity>): List<NoteModel> {
        return noteEntityList.map {
            NoteModel(
                id = it.id,
                noteId = it.noteId,
                note = it.note,
                topicId = it.topic_id,
                noteTitle = it.noteTitle
            )
        }
    }

    fun mapFromTopicEntityToTopicModelList(topicEntityList: List<TopicEntity>): List<TopicModel> {
        return topicEntityList.map {
            TopicModel(
                topic = it.topic,
                topicId = it.topicId
            )
        }
    }

}