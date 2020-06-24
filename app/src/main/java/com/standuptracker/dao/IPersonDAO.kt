package com.standuptracker.dao

import com.standuptracker.dto.Person
import retrofit2.Call
import retrofit2.http.GET

interface IPersonDAO {

    //todo change this!
    // set json endpoint
    @GET("/core/country-list/data_json/data/8c458f2d15d9f2119654b29ede6e45b8/data_json.json")
    fun getAllPeople(): Call<ArrayList<Person>>
}