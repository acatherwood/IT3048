package com.standuptracker.dto

import android.net.Uri
import java.util.*


data class Photo (var localUri : String = "", var remoteUri: String = "", var description: String  = "", var dateTaken : String = "", var id : String = "") {

}
