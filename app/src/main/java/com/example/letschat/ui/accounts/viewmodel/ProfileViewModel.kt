package com.example.letschat.ui.accounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.repository.FriendRequestRepository
import kotlinx.coroutines.launch

class ProfileViewModel:ViewModel() {

    private val userDao = UserDao()
    private val friendRequestDao = FriendRequestDao()
    private val friendRequestRepository = FriendRequestRepository(userDao,friendRequestDao)

     val allFriendRequests : LiveData<List<FriendRequest>> = friendRequestRepository.allFriendRequest
    init {
        viewModelScope.launch {
            friendRequestRepository.getAllMyFriendRequests()
        }
    }
}