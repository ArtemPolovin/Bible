package com.example.domain.repositories

import com.example.domain.models.CellHome
import io.reactivex.Single

interface ICellHomeRepo {
    fun getCellHomeList(): Single<List<CellHome>>
}