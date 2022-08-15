package com.example.letschat.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendRequest(
    val senderUser:User?=null,
    val receiverUser:User?=null,
    val status:Boolean?=null
):Parcelable
