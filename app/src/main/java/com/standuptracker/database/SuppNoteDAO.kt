package com.standuptracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuppNoteDAO {
    @Query("SELECT * FROM suppNote_table WHERE assocNote_id LIKE (:parentNoteID)")
    fun getSuppNotesByParentNoteID(parentNoteID:Int): SuppNote

    @Insert(onConflict= OnConflictStrategy.ABORT)
    suspend fun insert(suppNote: SuppNote)

    @Query("DELETE FROM suppNote_table")
    suspend fun deleteAll()
}