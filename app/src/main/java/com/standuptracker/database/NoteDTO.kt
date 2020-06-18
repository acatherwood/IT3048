package com.standuptracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName="note_table")
class Note(
    @PrimaryKey val noteID: Int,
    @ColumnInfo(name="note_content") val noteContent:String,
    @ColumnInfo(name="created_by") val createdBy: Int,
    @ColumnInfo(name="created_on") val createdOn: Date,
    @ColumnInfo(name="updated_on") val updatedOn: Date,
    @ColumnInfo(name="team_id") val teamID: Int
    )