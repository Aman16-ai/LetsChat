package com.example.letschat.data.repository

import android.bluetooth.le.AdvertiseData
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

    private var _friendRequestSendResponse : MutableLiveData<Boolean> = MutableLiveData()
    val friendRequestSendResponse : LiveData<Boolean>
    get() = _friendRequestSendResponse

    private var _friendRequestAcceptedResponse : MutableLiveData<Boolean> = MutableLiveData()
    val friendRequestAcceptedResponse : LiveData<Boolean>
        get() = _friendRequestAcceptedResponse

    suspend fun sendFriendRequest(receiverUser:User) {
        val sendUser = userDao.getCurrentUser()

        val myFriendRequest = FriendRequest(
            senderUser = sendUser,
            receiverUser = receiverUser,
            status = FriendRequestStatus.SENT
        )

         _friendRequestSendResponse.postValue(friendRequestDao.sendFriendRequest(myFriendRequest))
    }

    suspend fun getAllMyFriendRequests() {
        _allFriendRequests.postValue(friendRequestDao.getAllFriendRequest(userDao.getCurrentUser()!!))
    }
    suspend fun acceptFriendRequest(friendRequest:FriendRequest) {
        val result = friendRequestDao.updateFriendRequestStatus(friendRequest)
        var addedResultForSender :Boolean? = null
        var addedResultForCurrent:Boolean? = null
        if(result) {
            mAuth.uid?.let {
                friendRequest.senderUser?.let { friend ->
                    addedResultForSender = userDao.addFriend(it, friend)
                }
            }
            if(addedResultForSender!!) {
                friendRequest.senderUser?.uid?.let {
                    userDao.getCurrentUser()?.let { friend ->
                            addedResultForCurrent = userDao.addFriend(it, friend)
                        }
                }
            }
        }
//        _friendRequestAcceptedResponse.postValue(addedResultForSender!! && addedResultForCurrent!!)
        _friendRequestAcceptedResponse.postValue(true)
    }

}