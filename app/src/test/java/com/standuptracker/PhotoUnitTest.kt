package com.standuptracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.standuptracker.dto.Photo
import com.standuptracker.ui.home.HomeFragment
import com.standuptracker.ui.home.HomeViewModel
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PhotoUnitTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var hvm: HomeViewModel
    lateinit var hvf: HomeFragment

    @Test
    fun confirmPhoto_ouptsCorrectPhoto(){
        var photo = Photo(dateTaken = "07/14/2020" , description = "Final Test 2", id = "YHPtip4hwm6TKSuV9ffP",
            localUri = "content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200728_1111305190831273995192831.jpg",
            remoteUri = "https://firebasestorage.googleapis.com/v0/b/standup-tracker-ffd2f.appspot.com/o/images%2F9Ybag9SF1LaMwWC5B3OqfIFkOYx1%2FStandupTracker20200728_1111305190831273995192831.jpg?alt=media&token=7cfc72ea-49ac-4f6d-8677-d66bd8a7dc0c")
        assertTrue(photo.dateTaken.equals("07/14/2020"))
        assertTrue(photo.description.equals("Final Test 2"))
        assertTrue(photo.id.equals("YHPtip4hwm6TKSuV9ffP"))
        assertTrue(photo.localUri.equals("content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200728_1111305190831273995192831.jpg"))
        assertTrue(photo.remoteUri.equals("https://firebasestorage.googleapis.com/v0/b/standup-tracker-ffd2f.appspot.com/o/images%2F9Ybag9SF1LaMwWC5B3OqfIFkOYx1%2FStandupTracker20200728_1111305190831273995192831.jpg?alt=media&token=7cfc72ea-49ac-4f6d-8677-d66bd8a7dc0c"))
    }
    @Test
    fun photoDTO_toStringFormat(){
        var photo = Photo(dateTaken = "07/14/2020", description = "Final Test 2", id = "YHPtip4hwm6TKSuV9ffP",
           localUri = "content://com.standuptracker.android.fileprovider/my_root_images/Pictures/StandupTracker20200728_1111305190831273995192831.jpg",
            remoteUri = "https://firebasestorage.googleapis.com/v0/b/standup-tracker-ffd2f.appspot.com/o/images%2F9Ybag9SF1LaMwWC5B3OqfIFkOYx1%2FStandupTracker20200728_1111305190831273995192831.jpg?alt=media&token=7cfc72ea-49ac-4f6d-8677-d66bd8a7dc0c")
        assertTrue(photo.toString().equals("07/14/2020"))
    }
}
