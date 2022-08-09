package com.example.letschat.data.model

import com.google.firebase.Timestamp
import java.util.*

data class Message(
    val messagetxt:String? = null,
    val userId:String? = null,
    val timestamp: Timestamp?= Timestamp.now()
)