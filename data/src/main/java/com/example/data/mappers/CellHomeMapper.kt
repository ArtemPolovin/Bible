package com.example.data.mappers

import com.example.data.db.tables.CellHomeEntity
import com.example.domain.models.CellHome

class CellHomeMapper {
    fun mapFromCellHomeEntityListToCellHomeList(cellHomeEntity: List<CellHomeEntity>): List<CellHome> {
      return  cellHomeEntity.map {
            CellHome("@drawable/${it.imageName}",it.title)
        }
    }
}