package com.shobhith.rxandroidsample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shobhith.rxandroidsample.domain.model.Note
import com.shobhith.rxandroidsample.domain.use_case.GetNotes
import com.shobhith.rxandroidsample.domain.use_case.InsertNote
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val insertNote: InsertNote
) : ViewModel() {

    private val _notesState = MutableLiveData<NotesState>()
    val notesState: LiveData<NotesState> get() = _notesState

    val noteTitle = MutableLiveData<String>("")
    val noteDesc = MutableLiveData<String>("")

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        getNotes
            .getNotes()
            .subscribeOn(Schedulers.io())
            .map { it.sortedByDescending { note -> note.id } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _notesState.value = NotesState.NotesFetched(it)
                },
                {
                    _notesState.value = NotesState.Error(it.localizedMessage)
                }
            )
    }

    fun insertNotes() {
        val note = Note(title = noteTitle.value!!, content = noteDesc.value!!)
        insertNote
            .insertNotes(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _notesState.value = NotesState.NotesInserted
                },
                {
                    _notesState.value = NotesState.Error(it.localizedMessage)
                }
            )
    }
}