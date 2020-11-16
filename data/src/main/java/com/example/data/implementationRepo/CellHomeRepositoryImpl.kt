package com.example.data.implementationRepo

import android.util.Log
import com.example.data.db.dao.ICellHomeDao
import com.example.data.db.tables.CellHomeEntity
import com.example.data.mappers.CellHomeMapper
import com.example.domain.models.CellHome
import com.example.domain.repositories.ICellHomeRepo
import io.reactivex.Scheduler
import io.reactivex.Single

class CellHomeRepositoryImpl(
    private val cellHomeDao: ICellHomeDao,
    private val mapper: CellHomeMapper,
    private val schedulersIO: Scheduler
) : ICellHomeRepo {
    override fun getCellHomeList(): Single<List<CellHome>> {
        return cellHomeDao.getCellHomeList()
            .subscribeOn(schedulersIO)
            .map {
                mapper.mapFromCellHomeEntityListToCellHomeList(it)
            }
    }
}