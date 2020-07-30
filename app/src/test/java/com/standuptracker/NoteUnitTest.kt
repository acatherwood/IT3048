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


    @Test
    fun confirmNote_outputsCorrectNote() {
        var note = Note("09xH6mv19zXr6Yo2TPxT", "imageDisplayTest1", "7/22/2020", "content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200727_2203505562631059751486220.jpg")
        assertTrue(note.noteId.equals("09xH6mv19zXr6Yo2TPxT"))
        assertTrue(note.content.equals("imageDisplayTest1"))
        assertTrue(note.dateCreated.equals("7/22/2020"))
        assertTrue(note.localUri.equals("content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200727_2203505562631059751486220.jpg"))
    }
    @Test
    fun noteDTO_toStringFormat() {
        var note =  Note("09xH6mv19zXr6Yo2TPxT", "imageDisplayTest1", "7/22/2020", "content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200727_2203505562631059751486220.jpg")
        assertTrue(note.toString().equals("imageDisplayTest1"))
    }

}