package com.shobhith.rxandroidsample.data.data_source.local_db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.shobhith.rxandroidsample.domain.model.Note
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class NoteDaoTest {

    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = noteDatabase.noteDao
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun insertNote() {
        val note = Note(1, "Clean Room", "Clean living area")
        noteDao.insertNote(note).blockingAwait()
        noteDao.getNotes().test()
            .assertValue { it.contains(note) }
    }

    @Test
    fun deleteNote() {
        val note1 = Note(1, "Clean Room", "Clean living area")
        val note2 = Note(2, "Go Shopping", "Do grocery Shopping")
        noteDao.insertNote(note1).blockingAwait()
        noteDao.insertNote(note2).blockingAwait()
        noteDao.deleteNote(note2).blockingAwait()
        noteDao.getNotes().test()
            .assertValue { !it.contains(note2) }
    }
}