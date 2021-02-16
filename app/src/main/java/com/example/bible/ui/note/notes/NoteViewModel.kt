package com.example.bible.ui.note.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bible.ui.viewstate.NoteViewState
import com.example.domain.usecases.noteusecases.FetchNotesUseCase
import com.example.domain.usecases.noteusecases.RemoveNoteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.annotations.NotNull

class NoteViewModel(
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase
) : ViewModel() {

    private val disposeBag = CompositeDisposable()

    private val _noteList = MutableLiveData<NoteViewState>()
    val noteList: LiveData<NoteViewState> get() = _noteList

    private fun fetchNotes(topicId: Long) {
        _noteList.value = NoteViewState.Loading

        val disposable = fetchNotesUseCase(topicId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _noteList.value = NoteViewState.NotesLoaded(it)
                },
                {
                    _noteList.value = NoteViewState.Error
                    Log.i("Error", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(disposable)
    }

    private fun removeNote(noteId: Long) {
        val completable = removeNoteUseCase(noteId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i("Result", "removing note was successfully")
                },
                {
                    Log.i("ERROR", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(completable)
    }

    fun receiveRemovedNoteId(noteId: Long) {
        removeNote(noteId)
    }

    fun receiveTopicId(topicId: Long) {
        fetchNotes(topicId)
    }

    fun refreshNoteList(topicId: Long) {
        fetchNotes(topicId)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}