package com.shobhith.rxandroidsample.domain.use_case

import com.shobhith.rxandroidsample.domain.model.Note
import com.shobhith.rxandroidsample.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNotes(private val notesRepository: NotesRepository) {
    operator fun invoke() : Observable<List<Note>> = notesRepository.getNotes()
}