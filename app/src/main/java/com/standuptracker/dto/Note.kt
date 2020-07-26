package com.standuptracker.dto

import android.net.Uri
import com.google.firebase.Timestamp
import java.util.*

data class Note(var noteId:String = "", var content: String ="", var dateCreated: String = "", var localUri: String = "") {
    override fun toString(): String{
      return  "$content $dateCreated $localUri"

    }
}