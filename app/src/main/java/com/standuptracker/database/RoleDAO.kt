package com.standuptracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RoleDAO {
    @Query("SELECT * FROM role_table ORDER BY roleID ASC")
    fun getAllRoles(): Role

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(role:Role)

    @Query("DELETE FROM role_table")
    suspend fun deleteAll()

}