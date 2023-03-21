package com.shobhith.rxandroidsample.presentation

import com.shobhith.rxandroidsample.domain.model.Note

sealed class NotesState {
    data class NotesFetched(val notes: List<Note>) : NotesState()
    object NotesInserted : NotesState()
    data class Error(val message: String? = null) : NotesState()
}
