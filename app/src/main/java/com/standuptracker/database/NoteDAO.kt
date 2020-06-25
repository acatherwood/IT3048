package com.standuptracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO{
    @Query("SELECT * from note_table ORDER BY noteID DESC")
    fun getOrderedNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE created_by LIKE (:userID)")
    suspend fun getNotesByUser(userID:Int):LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE team_id LIKE (:teamID)")
    fun getNotesByTeam(teamID: Int): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE noteID LIKE (:noteID)")
    fun getNoteByID(noteID:Int): Note

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()
}