package com.example.data.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CellHomeEntity (
    @PrimaryKey(autoGenerate = true)
    val cellId: Int = 0,
    val imageName: String,
    val title: String

)