package com.standuptracker.dao

import com.standuptracker.dto.Person
import retrofit2.Call
import retrofit2.http.GET

interface IPersonDAO {

    /*
     * Sets the JSON endpoint
     */
    @GET("/2c321e9f2b38b5d3d1ed.json")
    fun getAllPeople(): Call<ArrayList<Person>>
}