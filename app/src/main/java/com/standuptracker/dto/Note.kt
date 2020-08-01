package com.standuptracker.dto

import android.net.Uri

data class Note(var noteId:String = "", var content: String ="", var dateCreated: String = "", var uri: String = "") {
    override fun toString(): String{

      return  "$content"

    }
}

