package com.standuptracker.dto

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id") var ID: Int,
    @SerializedName("email") var email: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("first_name") var firstName: String
) {
    /*
     * Sets string format to read as First Name, Last Name
     */
    override fun toString(): String {
        return "$firstName $lastName"
    }
}