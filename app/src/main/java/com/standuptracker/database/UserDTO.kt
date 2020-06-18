package com.standuptracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_table")
class User(
    @PrimaryKey val userID: Int,
    @ColumnInfo(name="first_name") val firstName: String,
    @ColumnInfo(name="last_name") val lastName: String,
    @ColumnInfo(name="email") val email: String,
    @ColumnInfo(name="address") val address: String,
    @ColumnInfo(name="rol_id") val roleID: Int

    )