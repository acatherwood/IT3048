package com.standuptracker.database

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class DatabaseRepository(private val userDao: UserDAO, private val noteDao: NoteDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allUsers: LiveData<List<User>> = userDao.getUsersOrderedByID()
    val allNotes: LiveData<List<Note>> = noteDao.getOrderedNotes()

    suspend fun notesByUser(userID:Int){
        noteDao.getNotesByUser(userID)
    }
    suspend fun insertNote(note:Note){
        noteDao.insert(note)
    }
    suspend fun insertUser(user:User) {
        userDao.insert(user)
    }
}