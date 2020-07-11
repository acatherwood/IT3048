package com.standuptracker.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.standuptracker.RetrofitClientInstance
import com.standuptracker.dao.IPersonDAO
import com.standuptracker.dto.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*
 * Retrieves list of people from JSON endpoint
 */
class PersonService {
    fun fetchPeople(): MutableLiveData<ArrayList<Person>> {
        var people = MutableLiveData<ArrayList<Person>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IPersonDAO::class.java)
        val call = service?.getAllPeople()
        // must put call on the background thread
        call?.enqueue(object : Callback<ArrayList<Person>> {
            override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) {
                Log.d("JSON", "Save Failed")
                throw Exception("failed to read JSON")
            }

            override fun onResponse(
                call: Call<ArrayList<Person>>,
                response: Response<ArrayList<Person>>
            ) {
                people.value = response.body()
            }

        })
        return people
    }
}