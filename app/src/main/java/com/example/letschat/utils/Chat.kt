package com.example.letschat.utils

import com.example.letschat.data.model.Message
import com.google.firebase.Timestamp

sealed class Chat {

    class content(val message: Message?) : Chat()
    class time(val timeStamp:Timestamp?) : Chat()
}