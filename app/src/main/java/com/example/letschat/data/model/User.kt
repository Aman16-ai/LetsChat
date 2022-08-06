package com.example.letschat.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid:String?=null,
    var profileImg:String?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var email:String?=null,
):Parcelable