package com.example.letschat.ui.accounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.model.User
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.launch

class FriendRequestViewModel:ViewModel() {
    private val userRepository = UserRepository()
    val allUserProfile : LiveData<List<User>> = userRepository.allUserProfile

    init {
       viewModelScope.launch {
           userRepository.getAllUserProfile()
       }
    }

        fun sendFriendRequest(receiverUser:User) {
        viewModelScope.launch {
            userRepository.sendFriendRequest(receiverUser)
        }
    }
}