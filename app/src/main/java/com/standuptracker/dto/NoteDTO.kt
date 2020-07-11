package com.standuptracker.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.Converter
import java.util.*

@Entity(tableName = "note_table")
class Note(@PrimaryKey(autoGenerate = true) val id: Int,
           @ColumnInfo(name="note") val note: String,
           @ColumnInfo(name="author") val author:String,
           @ColumnInfo(name="creation_date") val dateCreated: String)