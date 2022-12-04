package com.example.letschat.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun makeSpannableString(string: String,startIndex:Int,endIndex:Int): SpannableString {
    val spannable = SpannableString(string)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        startIndex,
        endIndex,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return spannable
}