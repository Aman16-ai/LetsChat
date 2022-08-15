package com.example.letschat.data.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MessageDao {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

    suspend fun saveMessage(message:Message,roomID:String): Boolean {
        val result = db
            .collection("chats")
            .document(roomID).collection("messages")
            .add(message)
            .await()
        return result!=null
    }

    suspend fun getAllMessage(roomID:String): MutableList<Message> {
        val messages = db.collection("chats")
            .document(roomID)
            .collection("messages")
            .get()
            .await()
            .toObjects(Message::class.java)
        messages.sortBy { it.timestamp?.seconds }
        return messages
    }

    fun getAllMessagesFromRoomInRealTime(roomID: String,callBack:(List<Message>)->Unit) {
        db.collection("chats")
            .document(roomID)
            .collection("messages")
            .addSnapshotListener{snapshot,e->
                if(e != null) {
                    Log.w("message", "listen faild")
                }
                else {
                    val message = snapshot?.toObjects(Message::class.java)
                    callBack(message as List<Message>)
                }
        }
    }
}