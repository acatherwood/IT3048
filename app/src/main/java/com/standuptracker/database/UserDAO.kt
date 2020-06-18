package com.standuptracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO{

    @Query("SELECT * from user_table ORDER BY userID DESC")
    fun getUsersByID(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}