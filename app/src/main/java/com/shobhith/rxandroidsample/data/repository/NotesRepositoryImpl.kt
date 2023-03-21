package com.shobhith.rxandroidsample.data.repository

import com.shobhith.rxandroidsample.data.data_source.local_db.NoteDao
import com.shobhith.rxandroidsample.domain.model.Note
import com.shobhith.rxandroidsample.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NotesRepositoryImpl(private val noteDao: NoteDao) : NotesRepository {
    override fun getNotes(): Observable<List<Note>> =
        noteDao.getNotes()

    override fun insertNotes(note: Note): Completable =
        noteDao.insertNote(note)
}