package com.standuptracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.standuptracker.dto.Note
import java.util.*

@Dao
interface NoteDAO {

    @Query("SELECT * from note_table ORDER BY id ASC")
    fun getAllNotesByIdASC(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY creation_date")
    fun getAllNotesByDate(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE creation_date LIKE (:searchDate)")
    fun getNotesByDate(searchDate: String): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()
}