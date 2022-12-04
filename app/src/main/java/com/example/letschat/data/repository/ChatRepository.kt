package com.example.letschat.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.dao.MessageDao
import com.example.letschat.data.model.Message
import com.example.letschat.data.model.SpamMessageRequest
import com.example.letschat.data.model.SpamMessageResponse
import com.example.letschat.data.service.SpamClassificationService
import com.example.letschat.utils.Chat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

class ChatRepository {

    private val messageDao = MessageDao()
    private val mAuth = FirebaseAuth.getInstance()
    private var _messages : MutableLiveData<List<Message>> = MutableLiveData()
    val messages: LiveData<List<Message>>
    get() = _messages

    private var _chat : MutableLiveData<List<Chat>> = MutableLiveData()
    val chat : LiveData<List<Chat>>
    get() = _chat

    fun getAllMessage(friendUserID: String) {
        val roomID = mAuth.uid + friendUserID
        val chatList : MutableList<Chat> = ArrayList()
        messageDao.getAllMessagesFromRoomInRealTime(roomID) { it ->
//            var time : Timestamp? = null
//            it.sortedBy { it.timestamp?.seconds }
//            for(i in it) {
//                if (time == null || time.toDate() != i.timestamp?.toDate()) {
//                    chatList.add(Chat.time(i.timestamp))
//                    time = i.timestamp
//                }
//                chatList.add((Chat.content(i)))
//            }
//            for (i in chatList) {
//                when(i) {
//                    is Chat.time -> {
//                        Log.d("chatlog", "Time ${i.timeStamp?.toDate()}")
//                    }
//                    is Chat.content -> {
//                        Log.d("chatlog", "Message ${i.message?.messagetxt}")
//                    }
//                }
//            }
            _messages.postValue(it)
//            _chat.postValue(chatList)
        }
    }
    private suspend fun classifyMessage(message:String):Boolean? {
        val spamMessageResponse : SpamMessageResponse? = SpamClassificationService
            .spamClassificationInstance
            .predictMessage(SpamMessageRequest(message))
            .body()
        return spamMessageResponse?.Spam

    }
    suspend fun sendMessage(message:String,friendUserID: String) {
        val isSpam = classifyMessage(message)
        Log.d("spam", "sendMessage: ${isSpam}")
        saveMessageToFireBase(message,mAuth.uid + friendUserID,isSpam)
        saveMessageToFireBase(message,friendUserID + mAuth.uid,isSpam)
    }
    suspend fun saveMessageToFireBase(message:String,roomID:String,isSpam:Boolean?):Boolean {
        val messageobj = Message(message,mAuth.uid!!, isSpam,Timestamp.now())
        return messageDao.saveMessage(messageobj,roomID)
    }
}