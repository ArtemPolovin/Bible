package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.tables.NoteEntity
import com.example.data.db.tables.TopicEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface INoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteEntity)

    @Insert
    fun insertTopic(topic: TopicEntity)

    @Query("SELECT * FROM topic_entity")
    fun getTopics(): Flowable<List<TopicEntity>>

    @Query("SELECT * FROM note_entity WHERE topic_id = :topicId")
    fun getNotesByTopicId(topicId: Long): Flowable<List<NoteEntity>>

    @Query("DELETE FROM topic_entity WHERE topicId = :topicId ")
    fun removeTopic(topicId: Long)

    @Query("DELETE FROM note_entity WHERE noteId = :noteId")
    fun removeNote(noteId: Long)

    @Query("DELETE FROM note_entity")
    fun deleteAllFromNoteEntity()

    @Query("DELETE FROM topic_entity")
    fun deleteAllFromTopicEntity()

}