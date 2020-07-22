package com.standuptracker

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.standuptracker.service.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.common.base.Converter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.standuptracker.dto.Note
import kotlinx.android.synthetic.main.activity_search_notes.*
import java.time.LocalDate
import java.util.*

private val firestore = Firebase.firestore

class SearchNotes : AppCompatActivity() {
    var mNoteArrayList: ArrayList<Note> = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_notes)
        btnCloseActivity.setOnClickListener{
            finish()
        }
        //foo()

        rcvNotes.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(this@SearchNotes)
            // set the custom adapter to the RecyclerView

            adapter = ListAdapter(mNoteArrayList)
        }
    }


   private fun foo() {
        firestore.collection("notes").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var _tempNoteContent = document.data["noteContent"].toString()
                    var _tempDateCreated = document.data["dateCreated"].toString()

                    var _tempNote = Note(_tempNoteContent,_tempDateCreated)
                    mNoteArrayList.add(_tempNote)
                }
                //Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


}