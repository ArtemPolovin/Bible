package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.tables.CellHomeEntity
import io.reactivex.Single

@Dao
interface ICellHomeDao {

    @Insert
    fun insertCell(cellHome: CellHomeEntity)

    @Query("SELECT * FROM cellhomeentity")
    fun getCellHomeList(): Single<List<CellHomeEntity>>

    @Delete
    fun removeCell(cell: CellHomeEntity)

}