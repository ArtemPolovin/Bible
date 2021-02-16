package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.db.dao.ICellHomeDao
import com.example.data.db.dao.INoteDao
import com.example.data.db.tables.CellHomeEntity
import com.example.data.db.tables.NoteEntity
import com.example.data.db.tables.TopicEntity

@Database(entities = [CellHomeEntity::class, TopicEntity::class,NoteEntity::class], version = 4, exportSchema = false)
abstract class Database : RoomDatabase()  {
    abstract fun getCellHomeDao(): ICellHomeDao
    abstract fun getNoteDao(): INoteDao

    /*companion object{
        val MIGRATION_3_4 = object :Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
               // database.execSQL("CREATE TABLE 'topic_entity'('id' INTEGER NOT NULL, 'topic' TEXT NOT NULL,  PRIMARY KEY('id'))")
                database.execSQL("ALTER TABLE note_entity ADD COLUMN noteTitle TEXT")
            }

        }
    }*/

}