package com.standuptracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.standuptracker.dto.Note
import com.standuptracker.ui.home.HomeFragment
import com.standuptracker.ui.home.HomeViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class NoteUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var hvm: HomeViewModel
    lateinit var hvf: HomeFragment

    var note = Note("IkXw9I0mU7gjymTSytza", "Test Note is Best Note", "7/18/2020", "https://firebasestorage.googleapis.com/v0/b/standup-tracker-ffd2f.appspot.com/o/images%2F9Ybag9SF1LaMwWC5B3OqfIFkOYx1%2FStandupTracker20200728_2035234074515540357557551.jpg?alt=media&token=46fd5635-2d88-4378-b2e4-2261fd75078e")
    @Test
    fun confirmNote_outputsCorrectNote() {
        assertTrue(note.noteId.equals("IkXw9I0mU7gjymTSytza"))
        assertTrue(note.content.equals("Test Note is Best Note"))
        assertTrue(note.dateCreated.equals("7/18/2020"))
        assertTrue(note.uri.equals("https://firebasestorage.googleapis.com/v0/b/standup-tracker-ffd2f.appspot.com/o/images%2F9Ybag9SF1LaMwWC5B3OqfIFkOYx1%2FStandupTracker20200728_2035234074515540357557551.jpg?alt=media&token=46fd5635-2d88-4378-b2e4-2261fd75078e"))
    }


    @Test
    fun noteDTO_toStringFormat() {
        assertTrue(note.toString().equals("Test Note is Best Note"))
    }

}