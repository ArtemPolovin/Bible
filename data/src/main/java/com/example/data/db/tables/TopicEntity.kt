package com.example.data.db.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "topic_entity", indices = [Index(value = ["topicId"], unique = true)])
class TopicEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val topicId: Long,
    val topic: String
)

