package com.standuptracker.dto

import com.google.gson.annotations.SerializedName

data class Person(@SerializedName("Code")var code:String, @SerializedName("Name")var name:String ) {
    //add to string override to format to read as Name Code
    override fun toString(): String {
        return "$name $code"
    }
}