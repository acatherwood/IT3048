package com.standuptracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="suppNote_table")
class SuppNote(
    @PrimaryKey val suppNoteID:Int,
    @ColumnInfo(name="suppNote_content") val suppNoteContent: String,
    @ColumnInfo(name="assocNote_id") val assocNoteID: Int
)