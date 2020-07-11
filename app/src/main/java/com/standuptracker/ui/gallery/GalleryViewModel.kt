package com.standuptracker.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.standuptracker.dto.Person
import com.standuptracker.service.PersonService

class GalleryViewModel : ViewModel() {

    var people: MutableLiveData<ArrayList<Person>> = MutableLiveData<ArrayList<Person>>()
    private var personService: PersonService = PersonService()

    init {
        fetchPeople()
    }

    private fun fetchPeople() {
        //read json response into countries variable
        people = personService.fetchPeople()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Find a Team Member"
    }
    val text: LiveData<String> = _text
}