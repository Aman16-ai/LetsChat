package com.example.letschat.ui.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    private val userRepository = UserRepository()

    var allUsers = userRepository.allUser

    init {
        viewModelScope.launch {
            userRepository.getAllUsers()
        }
    }
}