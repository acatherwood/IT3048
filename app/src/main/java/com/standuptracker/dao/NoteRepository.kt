package com.standuptracker.dao

import androidx.lifecycle.LiveData
import com.standuptracker.dto.Note

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDao: NoteDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotesByDate()

    suspend fun insert(note:Note) {
        noteDao.insert(note)
    }
}