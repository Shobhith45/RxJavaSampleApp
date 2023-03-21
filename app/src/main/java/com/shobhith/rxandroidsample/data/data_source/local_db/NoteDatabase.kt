package com.shobhith.rxandroidsample.data.data_source.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shobhith.rxandroidsample.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}