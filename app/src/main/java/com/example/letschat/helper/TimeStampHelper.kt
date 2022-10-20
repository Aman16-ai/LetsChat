package com.example.letschat.helper

import java.util.*

fun getDateTime(dateStr:String):String {
    return dateStr.slice(IntRange(0,dateStr.length-16))
}