package com.standuptracker.dto

import android.net.Uri
import java.util.*

data class Note(val content: String, val dateCreated: String, val localUri: String = "")