package com.standuptracker.dto

import java.util.*

data class Photo (var localUri : String = "", var remoteUri: String = "", var descirption: String  = "", var dateTaken : Date = Date()) {
}
