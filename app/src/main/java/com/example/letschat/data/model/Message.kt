package com.example.letschat.data.model

import com.google.firebase.Timestamp
import java.util.*

data class Message(
    val messagetxt:String="",
    val userId:String="",
    val timestamp: Timestamp = Timestamp.now()
)