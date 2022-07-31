package com.example.letschat.ui.chat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.model.Message
import com.example.letschat.data.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val chatRepository = ChatRepository()


    fun getAllMessages(friendUserId: String): LiveData<List<Message>> {
        chatRepository.getAllMessage(friendUserId)
        return chatRepository.messages
    }
    fun sendMessage(message: String,friendUserID:String) {
        viewModelScope.launch {
            chatRepository.sendMessage(message,friendUserID)
        }
    }
}