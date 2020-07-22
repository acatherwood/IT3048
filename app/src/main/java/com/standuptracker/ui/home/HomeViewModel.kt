package com.standuptracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.standuptracker.dto.Note
import com.standuptracker.dto.Photo
import java.util.ArrayList

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "View Standup Notes By Date"
    }

    val text: LiveData<String> = _text


    fun save(
        //note : Note,
        photos: java.util.ArrayList<Photo>
    ) {
       // val document = firebase.collection(collectionPath: )
        if (photos != null && photos.size > 0) {
            savePhotos(photos)
        }
    }

    fun savePhotos(
        photos: java.util.ArrayList<Photo>
    ) {
    }
}

