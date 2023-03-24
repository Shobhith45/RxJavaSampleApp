package com.shobhith.rxandroidsample.di

import android.app.Application
import androidx.room.Room
import com.shobhith.rxandroidsample.data.data_source.local_db.NoteDatabase
import com.shobhith.rxandroidsample.data.repository.NotesRepositoryImpl
import com.shobhith.rxandroidsample.domain.repository.NotesRepository
import com.shobhith.rxandroidsample.domain.use_case.DeleteNote
import com.shobhith.rxandroidsample.domain.use_case.GetNotes
import com.shobhith.rxandroidsample.domain.use_case.InsertNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDataBase(application: Application) : NoteDatabase{
        return Room.databaseBuilder(application, NoteDatabase::class.java, NoteDatabase.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDataBase: NoteDatabase) : NotesRepository{
        return NotesRepositoryImpl(noteDataBase.noteDao)
    }

    @Provides
    @Singleton
    fun provideGetNoteUseCase(noteRepository: NotesRepository) : GetNotes {
        return GetNotes(noteRepository)
    }

    @Provides
    @Singleton
    fun provideInsertNoteUseCase(noteRepository: NotesRepository) : InsertNote {
        return InsertNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NotesRepository) : DeleteNote {
        return DeleteNote(noteRepository)
    }
}