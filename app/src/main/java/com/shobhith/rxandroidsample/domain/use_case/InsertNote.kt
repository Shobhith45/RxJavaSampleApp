package com.shobhith.rxandroidsample.domain.use_case

import com.shobhith.rxandroidsample.domain.model.Note
import com.shobhith.rxandroidsample.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class InsertNote(private val notesRepository: NotesRepository) {
    operator fun invoke(note: Note) : Completable = notesRepository.insertNotes(note)
}