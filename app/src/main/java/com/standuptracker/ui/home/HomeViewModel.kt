package com.standuptracker.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.standuptracker.dao.NoteRepository
import com.standuptracker.dto.Note
import com.standuptracker.service.NoteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: NoteRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteRoomDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    private val _text = MutableLiveData<String>().apply {
        value = "View Standup Notes By Date"
    }
    val text: LiveData<String> = _text
}