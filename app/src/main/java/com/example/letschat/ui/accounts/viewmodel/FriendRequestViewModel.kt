package com.example.letschat.ui.accounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.model.User
import com.example.letschat.data.repository.FriendRequestRepository
import com.example.letschat.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class FriendRequestViewModel:ViewModel() {
    private val userRepository = UserRepository()
    private val friendRequestDao = FriendRequestDao()
    private val userDao = UserDao()
    private val mAuth = FirebaseAuth.getInstance()

    private val friendRequestRequestRepository = FriendRequestRepository(userDao,friendRequestDao,mAuth)
    val allUserProfile : LiveData<List<User>> = userRepository.allUserProfile

    val friendRequestSendReponse : LiveData<Boolean> = friendRequestRequestRepository.friendRequestSendResponse

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