package com.standuptracker.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.standuptracker.dao.NoteDAO
import com.standuptracker.dto.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Types.NULL
import java.time.Instant
import java.util.*

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDAO

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()

                    // Delete all content here.
                    noteDao.deleteAll()

                    // Add sample words.
                    var note = Note(NULL,"First Note", "Trevor Cromwell", Date.from(Instant.now()).toString())
                    noteDao.insert(note)
                }
            }
       }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NoteRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
               // return instance
                instance
            }
        }
    }
}