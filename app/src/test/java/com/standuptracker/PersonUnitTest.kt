package com.standuptracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.standuptracker.ui.gallery.GalleryViewModel
import com.standuptracker.dto.Person
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PersonUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var gvm: GalleryViewModel

    @Before
    fun populatePeople() {
        gvm = GalleryViewModel()
    }

    @Test
    fun countryDTO_maintainsState() {
        var person = Person(1, "rfrith0@stanford.edu", "Female", "Frith", "Roxanna")
        Assert.assertTrue(person.email.equals("rfrith0@stanford.edu"))
        Assert.assertTrue(person.gender.equals("Female"))
        Assert.assertTrue(person.lastName.equals("Frith"))
        Assert.assertTrue(person.firstName.equals("Roxanna"))
    }

    @Test
    fun personDTO_toStringFormat() {
        var person =  Person(1, "rfrith0@stanford.edu", "Female", "Frith", "Roxanna")
        Assert.assertTrue(person.toString().equals("Roxanna Frith"))
    }

    @Test
    fun personDTO_isPopulated() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenTheCollectionSizeShouldBeGreaterThanZero()
    }

    private fun givenViewModelIsInitialized() {

    }

    private fun whenJSONDataAreReadAndParsed() {
        gvm.fetchPeople()
    }

    private fun thenTheCollectionSizeShouldBeGreaterThanZero() {
        var allPeople = ArrayList<Person>()

        gvm.people.observeForever{
            allPeople = it
        }
        Thread.sleep(5000)
        Assert.assertNotNull(allPeople)
        Assert.assertTrue(allPeople.size > 0)
    }

    @Test
    fun personDTO_containsSilvester() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenResultsShouldContainSilvester()
    }

    private fun thenResultsShouldContainSilvester() {
        var containsSilvester:Boolean = false
        gvm.people.observeForever {
            it.forEach {
                if (it.firstName.equals("Silvester")) {
                    containsSilvester = true

                }
            }
            Assert.assertTrue(containsSilvester)
        }
    }
}