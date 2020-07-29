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

//    @Test
//    fun searchForFinalTest2_returnsFinalTest2(){
//        givenMockedNotesAreAvailable()
//        whenSearchByDateForJuly142020()
//        thenResultContainsFinalTest2()
//
//    }
//
//    private fun givenMockedNotesAreAvailable() {
//
//    }
//
//    private fun whenSearchByDateForJuly142020() {
//        hvm = HomeViewModel()
//        hvm.filterNotes("7/14/2020")
//    }
//
//
//    private fun thenResultContainsFinalTest2() {
//        var finalTest2Found = false
//        hvm.notes.observeForever{
//            assertNotNull(it)
//            assertTrue(it.size > 0)
//            it.forEach {
//                if (it.content == "Final Test 2" && it.dateCreated == "7/14/2020"){
//                    finalTest2Found = true
//            }
//
//            }
//        }
//        assertTrue(finalTest2Found)
//    }


}