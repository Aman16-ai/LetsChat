package com.example.letschat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.dao.MessageDao
import com.example.letschat.data.model.Message
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

class ChatRepository {

    private val messageDao = MessageDao()
    private val mAuth = FirebaseAuth.getInstance()
    private var _messages : MutableLiveData<List<Message>> = MutableLiveData()
    val messages: LiveData<List<Message>>
    get() = _messages

    fun getAllMessage(friendUserID: String) {
        val roomID = mAuth.uid + friendUserID
        messageDao.getAllMessagesFromRoomInRealTime(roomID) {
            _messages.postValue(it)
        }
    }

    suspend fun sendMessage(message:String,friendUserID: String) {
        saveMessageToFireBase(message,mAuth.uid + friendUserID)
        saveMessageToFireBase(message,friendUserID + mAuth.uid)
    }
    suspend fun saveMessageToFireBase(message:String,roomID:String):Boolean {
        val message = Message(message,mAuth.uid!!, Timestamp.now())
        return messageDao.saveMessage(message,roomID)
    }
}