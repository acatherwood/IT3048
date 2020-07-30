package com.standuptracker.ui.home

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.standuptracker.dto.Note
import com.standuptracker.dto.Photo
import java.lang.Exception

import kotlin.collections.ArrayList

private var storageReference = FirebaseStorage.getInstance().reference
private lateinit var firestore: FirebaseFirestore
private var _notes: MutableLiveData<ArrayList<Note>> = MutableLiveData<ArrayList<Note>>()
private lateinit var _note : Note
private var _user: FirebaseUser? = null
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
                    val id = it.data?.get("noteId").toString()
                    val day: String = it.data?.get("dateCreated").toString()
                    val content = it.data?.get("content").toString()
                    val remotePhoto = it.data?.get("uri").toString()
                    val note = Note(content = content, dateCreated = day, noteId=id, uri = remotePhoto)
                    allNotes.add(note)
                }
                _notes.value = allNotes
            }
        }
    }

    fun filterNotes(info:String) {
        firestore.collection("notes").whereEqualTo("dateCreated",info).addSnapshotListener {
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
                    val id = it.data?.get("noteId").toString()
                    val day: String = it.data?.get("dateCreated").toString()
                    val content = it.data?.get("content").toString()
                    val remotePhoto = it.data?.get("uri").toString()
                    val note = Note(content = content, dateCreated = day,noteId = id, uri = remotePhoto)
                    allNotes.add(note)
                }
                _notes.value = allNotes
            }
        }
    }

    fun save(
        note: Note,
        photo: Photo,
        user: FirebaseUser
    ) {
        if(user!=null){
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
                savePhoto(note,photo,user)
            }
            set.addOnFailureListener {
                Log.d("Firebase", "Save Failed")
            }
        } else {
            throw Exception("You must login as a valid user!")
        }
    }

    private fun savePhoto(
        note:Note,
        photo: Photo,
        user: FirebaseUser
    ) {
        val collection = firestore.collection("notes")
            .document(note.noteId)
            .collection("photos")
            val task = collection.add(photo)
            task.addOnSuccessListener {
                photo.id = it.id
                photo.description = note.content
                photo.dateTaken = note.dateCreated

                uploadPhoto(note, photo, user)
            }
        }


    private fun uploadPhoto(note:Note, photo:Photo, user: FirebaseUser) {
            var uri = Uri.parse(photo.localUri.toString())
            val imageRef = storageReference.child("images/" + user.uid + "/" + uri.lastPathSegment)
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnSuccessListener {
                val downloadUrl = imageRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    photo.remoteUri = it.toString()
                    note.uri= it.toString()
                    // update our Cloud Firestore with the public image URI.
                    updatePhotoDatabase(note, photo)
                }

            }
            uploadTask.addOnFailureListener {
                Log.e(TAG, it.message)
            }
        }

    private fun updatePhotoDatabase(note:Note, photo: Photo) {
        firestore.collection("notes")
            .document(note.noteId)
            .collection("photos")
            .document(photo.id)
            .set(photo)
        firestore.collection("notes")
            .document(note.noteId).set(note)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Team Stand Up Notes"
    }
    
    val text: LiveData<String> = _text


    internal var notes:MutableLiveData<ArrayList<Note>>
        get() { return _notes}
        set(value) {_notes = value}

    internal var note: Note
        get() {return _note}
        set(value) {_note = value}


}