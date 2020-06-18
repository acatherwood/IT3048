package com.standuptracker.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DatabaseRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private val allUsers: LiveData<List<User>>
    private val allNotes: LiveData<List<Note>>


    init {
        val userDAO = AppDatabase.getDatabase(application).userDAO()
        val noteDAO = AppDatabase.getDatabase(application).noteDAO()
        repository = DatabaseRepository(userDAO,noteDAO)
        allUsers = repository.allUsers
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
    fun insert(user:User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }
}