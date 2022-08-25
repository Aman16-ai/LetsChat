package com.example.letschat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.constants.FriendRequestStatus
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FriendRequestRepository(
    private val userDao: UserDao,
    private val friendRequestDao: FriendRequestDao,
    private val mAuth:FirebaseAuth
) {


    private var _allFriendRequests : MutableLiveData<List<FriendRequest>> = MutableLiveData()
    val allFriendRequest :LiveData<List<FriendRequest>>
    get() = _allFriendRequests


    suspend fun sendFriendRequest(receiverUser:User) {
        val sendUser = userDao.getCurrentUser()

        val myFriendRequest = FriendRequest(
            senderUser = sendUser,
            receiverUser = receiverUser,
            status = FriendRequestStatus.SENT
        )

        friendRequestDao.sendFriendRequest(myFriendRequest)
    }

    suspend fun getAllMyFriendRequests() {
        _allFriendRequests.postValue(friendRequestDao.getAllFriendRequest(userDao.getCurrentUser()!!))
    }
    suspend fun acceptFriendRequest(friendRequest:FriendRequest) {
        friendRequestDao.updateFriendRequestStatus(friendRequest)
        mAuth.uid?.let { friendRequest.senderUser?.let { friend -> userDao.addFriend(it, friend) } }
        friendRequest.senderUser?.uid?.let { userDao.getCurrentUser()
            ?.let { friend -> userDao.addFriend(it, friend) } }
    }

}