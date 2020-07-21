package com.standuptracker

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.standuptracker.service.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.standuptracker.dto.Note
import kotlinx.android.synthetic.main.activity_search_notes.*
import java.util.*

private val firestore = Firebase.firestore
class SearchNotes : AppCompatActivity() {

    private val mNotesList = listOf(
        Note("Test", Date()),
        Note("hello", Date())
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_notes)
        btnCloseActivity.setOnClickListener{
            finish()
        }
        rcvNotes.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(this@SearchNotes)
            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(mNotesList)
        }
    }


/*    private fun foo() {
        firestore.collection("notes").whereEqualTo("dateCreated",txtDate.toString()).get()
            .addOnSuccessListener { /*result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.println(Log.INFO,TAG,"${document.id}")
                }*/
                //Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }*/


}