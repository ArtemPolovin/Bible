package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.ICellHomeDao
import com.example.data.db.tables.CellHomeEntity

@Database(entities = [CellHomeEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase()  {
    abstract fun getCellHomeDao(): ICellHomeDao

}