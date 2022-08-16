package com.example.letschat.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val user:User?=null,
    var lastMessage:String?=null,
    var lastMessageSenderId :String?=null
):Parcelable
