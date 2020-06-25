package com.standuptracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="role_table")
class Role(
    @PrimaryKey val roleID: Int,
    @ColumnInfo(name="role_name") val roleName:String
)