package com.example.domain.usecases

import com.example.domain.repositories.ICellHomeRepo

class GetCellHomeListUseCase(private val cellHomeRepo: ICellHomeRepo) {
    fun invoke() = cellHomeRepo.getCellHomeList()
}