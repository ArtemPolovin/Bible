package com.example.data.db.dao

import androidx.room.*
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

    @Update
    fun updateCellHome(cellHome: CellHomeEntity): Int

    @Query("DELETE  FROM cellhomeentity")
    fun removeAll()

}