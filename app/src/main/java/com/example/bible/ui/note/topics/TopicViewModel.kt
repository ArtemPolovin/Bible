package com.example.bible.ui.note.topics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bible.ui.viewstate.NoteViewState
import com.example.domain.usecases.noteusecases.FetchTopicsUseCase
import com.example.domain.usecases.noteusecases.InsertTopicToDBUseCase
import com.example.domain.usecases.noteusecases.RemoveTopicUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TopicViewModel(
    private val fetchTopicsUseCase: FetchTopicsUseCase,
    private val insertTopicToDBUseCase: InsertTopicToDBUseCase,
    private val removeTopicUseCase: RemoveTopicUseCase
) : ViewModel() {

    private val disposeBag = CompositeDisposable()

    private var _topicList = MutableLiveData<NoteViewState>()
    val topicList: LiveData<NoteViewState> get() = _topicList

    private fun fetchTopics() {
        _topicList.value = NoteViewState.Loading

        val disposable = fetchTopicsUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _topicList.value = NoteViewState.TopicsLoaded(it)
                },
                {
                    _topicList.value = NoteViewState.Error
                    Log.i("Error", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(disposable)
    }

    private fun addNewTopic(topic: String) {
        val completable = insertTopicToDBUseCase(topic)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i("Result", "adding topic was successful")
                },
                {
                    Log.i("ERROR", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(completable)
    }

    private fun removeTopic(topicId: Long) {
        val completable = removeTopicUseCase(topicId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i("Result", "removing topic was successfully")
                },
                {
                    Log.i("ERROR", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(completable)
    }

    fun refreshTopicsList() {
        fetchTopics()
    }

    fun receiveNewTopic(topic: String) {
        addNewTopic(topic)
    }

    fun receiveRemovedTopic(topicId: Long) {
        removeTopic(topicId)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}