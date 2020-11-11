package com.example.bible.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bible.utils.BookViewState
import com.example.domain.usecases.GetBooksListUseCase
import com.example.domain.usecases.GetCellBookListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
    getBooksListUseCase: GetBooksListUseCase,
    private val getCellBookListUseCase: GetCellBookListUseCase
) : ViewModel() {

    private var disposeBag = CompositeDisposable()

    private val _bookViewState = MutableLiveData<BookViewState>()
    val bookViewState: LiveData<BookViewState> get() = _bookViewState

  private  val bookList = getBooksListUseCase()

    private var charMatchPercentage = 80
    private var wordMatchPercentage = 80

    private fun setupBookViewState(inputText: String) {

        _bookViewState.value = BookViewState.Loading

        val disposable = getCellBookListUseCase.invoke(
            bookList,
            inputText,
            wordMatchPercentage,
            charMatchPercentage
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _bookViewState.value = BookViewState.CellBooksLoaded(it)
                },
                {
                    _bookViewState.value = BookViewState.Error
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
        disposeBag.add(disposable)
    }

    fun setCharMatchPercentage(percentage: Int) {
        charMatchPercentage = percentage
        Log.i("OOOOOOOOOOOOOO","char percentage = $charMatchPercentage")
    }

    fun setWordMatchPercentage(percentage: Int) {
        wordMatchPercentage = percentage
    }

    fun sendInputText(inputText: String) {
        setupBookViewState(inputText)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}