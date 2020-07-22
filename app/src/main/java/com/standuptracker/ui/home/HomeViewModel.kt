package com.standuptracker.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.standuptracker.dto.Note
import java.util.*
import kotlin.collections.ArrayList

private lateinit var firestore: FirebaseFirestore
private var _notes: MutableLiveData<ArrayList<Note>> = MutableLiveData<ArrayList<Note>>()
private lateinit var _note : Note


class HomeViewModel : ViewModel() {

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToNotes()
    }

    /**
     * This will hear any updates from Firestore
     */
    private fun listenToNotes() {


        /**TODO: Make listbox only populate notes from user-selected date. */
        firestore.collection("notes").addSnapshotListener {
                snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                // now, we have a populated shapshot
                val allNotes = ArrayList<Note>()
                val documents = snapshot.documents
                documents.forEach {
                    val day: String = it.data?.get("dateCreated").toString()
                    val content = it.data?.get("content").toString()
                   // val noteId = it.data?.get("noteId").toString()
                    val note = Note(content = content, dateCreated = day)
                    allNotes.add(note!!)
                }
                _notes.value = allNotes
            }
        }
    }

    fun save(
        note: Note
       // photos: java.util.ArrayList<Photo>,
       // user: FirebaseUser
    ) {
        val document =
            if (note.noteId != null && !note.noteId.isEmpty()) {
                // updating existing
                firestore.collection("notes").document(note.noteId)
            } else {
                // create new
                firestore.collection("notes").document()
            }
        note.noteId = document.id
        val set = document.set(note)
        set.addOnSuccessListener {
            Log.d("Firebase", "document saved")
        }
        set.addOnFailureListener {
            Log.d("Firebase", "Save Failed")
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "View Standup Notes By Date"
    }
    
    val text: LiveData<String> = _text

    internal var notes:MutableLiveData<ArrayList<Note>>
        get() { return _notes}
        set(value) {_notes = value}

    internal var note: Note
        get() {return _note}
        set(value) {_note = value}
}
