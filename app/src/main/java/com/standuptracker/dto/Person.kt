package com.standuptracker.dto

import com.google.gson.annotations.SerializedName

data class Person(@SerializedName("id")var ID:Int, @SerializedName("email")var email:String, @SerializedName("gender") var gender: String, @SerializedName("last_name") var lastName:String, @SerializedName("first_name")var firstName: String ) {
    //add to string override to format to read as Name Code
    override fun toString(): String {
        return "$firstName $lastName + ' | ' + $email"
    }
}