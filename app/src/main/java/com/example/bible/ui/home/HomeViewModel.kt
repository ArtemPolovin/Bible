package com.example.bible.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.CellHome
import com.example.domain.usecases.GetCellHomeListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class HomeViewModel(
    private val getCellHomeListUseCase: GetCellHomeListUseCase
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _cellHomeList = MutableLiveData<List<CellHome>>()
    val cellHomeList: LiveData<List<CellHome>> get() = _cellHomeList

    init {
        getCellHomeList()
    }

    private fun getCellHomeList() {
        disposable = getCellHomeListUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _cellHomeList.value = it
                }
                ,{
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}