package com.example.letschat.ui.accounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.repository.FriendRequestRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileViewModel:ViewModel() {

    private val userDao = UserDao()
    private val friendRequestDao = FriendRequestDao()
    private val mAuth = FirebaseAuth.getInstance()

    private val friendRequestRepository = FriendRequestRepository(userDao,friendRequestDao,mAuth)

     val allFriendRequests : LiveData<List<FriendRequest>> = friendRequestRepository.allFriendRequest
    val acceptResponse = friendRequestRepository.friendRequestAcceptedResponse
    init {
        viewModelScope.launch {
            friendRequestRepository.getAllMyFriendRequests()
        }
    }

    fun acceptFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch {
            friendRequestRepository.acceptFriendRequest(friendRequest)
        }
    }
}