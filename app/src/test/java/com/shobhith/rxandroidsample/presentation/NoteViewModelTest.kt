package com.shobhith.rxandroidsample.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.rxandroidsample.RxSchedulerRule
import com.shobhith.rxandroidsample.domain.model.Note
import com.shobhith.rxandroidsample.domain.use_case.DeleteNote
import com.shobhith.rxandroidsample.domain.use_case.GetNotes
import com.shobhith.rxandroidsample.domain.use_case.InsertNote
import com.shobhith.rxandroidsample.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class NoteViewModelTest {

    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @get: Rule
    var rxSchedulerRule = RxSchedulerRule()

    @MockK
    private lateinit var insertNote: InsertNote

    @MockK
    private lateinit var deleteNote: DeleteNote

    @MockK
    private lateinit var getNotes: GetNotes

    private lateinit var noteViewModel: NoteViewModel

    private val notes = listOf(
        Note(1, "Shopping", "Do Grocery Shopping"),
        Note(2, "Cleaning", "Clean the House"),
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        noteViewModel = NoteViewModel(getNotes, insertNote, deleteNote)
    }


    @Test
    fun `given insert note when success should return NotesInserted state`() {
            every { insertNote(Note(0, anyString(), anyString())) } returns Completable.complete()
            noteViewModel.insertNotes()
            val actualState = noteViewModel.notesState.getOrAwaitValue()
            val expected = NotesState.NotesInserted
            assertThat(actualState).isEqualTo(expected)
    }

    @Test
    fun `given delete note when success should return NotesDeleted state`() {
        val note = Note(0, anyString(), anyString())
        every { deleteNote(note) } returns Completable.complete()
        noteViewModel.deleteNotes(note)
        val actualState = noteViewModel.notesState.getOrAwaitValue()
        val expected = NotesState.NotesDeleted
        assertThat(actualState).isEqualTo(expected)
    }

    @Test
    fun `given fetch notes when success should return NotesFetched state with list of notes`() {
        every { getNotes() } returns Observable.just(notes)
        noteViewModel.fetchNotes()
        val actualState = noteViewModel.notesState.getOrAwaitValue()
        assertThat( actualState.notes?.containsAll(notes)).isTrue()
    }
}