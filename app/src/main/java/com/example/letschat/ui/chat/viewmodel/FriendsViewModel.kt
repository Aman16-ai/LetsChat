package com.example.letschat.ui.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.model.Message
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsViewModel : ViewModel() {

    private val userRepository = UserRepository()
    var allUsers = userRepository.allUser
    init {
        viewModelScope.launch {
            userRepository.getAllUsers()
        }
    }
}