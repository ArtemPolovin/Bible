package com.example.bible.ui.note.notepaage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.NoteModel
import com.example.domain.usecases.noteusecases.InsertNoteUseCase
import com.example.domain.usecases.noteusecases.RemoveNoteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WriteNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    private val disposeBag = CompositeDisposable()


    private fun saveNote(note: String, topicId: Long, noteTitle: String, id: Int) {

        val completable = insertNoteUseCase(note, topicId, noteTitle, id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i("Result", "inserting note to db was successful")
                },
                {
                    Log.i("ERROR", "${it.printStackTrace()}")
                }
            )
        disposeBag.add(completable)
    }

    fun receiveCreatedNoteData(note: String, topicId: Long, noteTitle: String,id:Int) {
        saveNote(note,topicId,noteTitle,id)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}