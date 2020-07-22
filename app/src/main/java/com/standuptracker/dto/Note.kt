package com.standuptracker.dto

import android.net.Uri
import java.util.*

data class Note(var noteId:String = "", var content: String ="", var dateCreated: String, var localUri: String = "") {
    override fun toString(): String{
      return  "$noteId $content $dateCreated $localUri"

    }
}