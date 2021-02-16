package com.example.data.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "note_entity",
    foreignKeys = [ForeignKey(
        entity = TopicEntity::class,
        parentColumns = ["topicId"],
        childColumns = ["topic_id"],
        onDelete = CASCADE
    )]
)
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteId: Long,
    val note: String,
    val noteTitle: String?,
    @ColumnInfo(index = true)
    val topic_id: Long
)