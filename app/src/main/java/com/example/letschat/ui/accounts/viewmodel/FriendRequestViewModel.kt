package com.example.letschat.ui.accounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.User
import com.example.letschat.data.repository.FriendRequestRepository
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.launch

class FriendRequestViewModel:ViewModel() {
    private val userRepository = UserRepository()
    private val friendRequestDao = FriendRequestDao()
    private val userDao = UserDao()
    private val friendRequestRequestRepository = FriendRequestRepository(userDao,friendRequestDao)
    val allUserProfile : LiveData<List<User>> = userRepository.allUserProfile

    init {
       viewModelScope.launch {
           userRepository.getAllUserProfile()
       }
    }

        fun sendFriendRequest(receiverUser:User) {
        viewModelScope.launch {
            friendRequestRequestRepository.sendFriendRequest(receiverUser)
        }
    }
}