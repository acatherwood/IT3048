package com.standuptracker.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.standuptracker.RetrofitClientInstance
import com.standuptracker.dao.IPersonDAO
import com.standuptracker.dto.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PersonService {
    fun fetchPeople() : MutableLiveData<ArrayList<Person>> {
        val people = MutableLiveData<ArrayList<Person>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IPersonDAO::class.java)
        val call = service?.getAllPeople()
        // must put call on the background thread
        call?.enqueue(object: Callback<ArrayList<Person>> {
            override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) {
                throw Exception("failed to read JSON")
                Log.d("JSON", "Save Failed")
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