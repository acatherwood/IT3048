package com.standuptracker.dto

data class Note(var noteId:String = "", var content: String ="", var dateCreated: String = "", var localUri: String = "") {
    override fun toString(): String{

      return  "$content $dateCreated $localUri"

    }
}

