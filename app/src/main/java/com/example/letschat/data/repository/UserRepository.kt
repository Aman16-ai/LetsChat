package com.example.letschat.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.dao.FriendRequestDao
import com.example.letschat.data.dao.MessageDao
import com.example.letschat.data.dao.UserDao
import com.example.letschat.data.model.Friend
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.model.Message
import com.example.letschat.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository {
    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private var userAuthState : MutableLiveData<Boolean> = MutableLiveData()
    private var userdoa : UserDao = UserDao()
    private var messageDao : MessageDao = MessageDao()
    private var friendRequestDao : FriendRequestDao = FriendRequestDao()

    private var _allUserProfile : MutableLiveData<List<User>> = MutableLiveData()
    val allUserProfile : LiveData<List<User>>
    get() = _allUserProfile

    private var _allUsers:MutableLiveData<List<Friend>> = MutableLiveData()
    val allUser : LiveData<List<Friend>>
    get() = _allUsers


    init {
        userAuthState.value = mAuth.currentUser != null
    }
    suspend fun uploadProfileImg(profileImgUri: Uri,imgFileType:String): String? {
        return userdoa.saveUserProfileImgFile(profileImgUri,imgFileType)
    }
    suspend fun registerUser(firstName:String, lastName:String,imgUri:Uri ,imgtype:String, email:String, password:String) {
        try {
            val result:AuthResult = mAuth.createUserWithEmailAndPassword(email,password)
                .await()
            if(result.user != null) {
                val imgUrl = uploadProfileImg(imgUri, imgtype)
                val user = User(uid = mAuth.uid,firstName = firstName,lastName = lastName,email = email, profileImg = imgUrl)
                val fireStoreResult :Boolean = userdoa.saveUserDetails(user)
                userAuthState.postValue(true)
            }
            else {
                userAuthState.postValue(false)
            }
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    suspend fun loginUser(email:String,password: String) {
        try {
            val result : AuthResult = mAuth.signInWithEmailAndPassword(email,password).await()
            userAuthState.value = result.user != null
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    fun logoutUser() {
        mAuth.signOut()
        userAuthState.value = false
    }
    fun getUserAuthState() :MutableLiveData<Boolean> {
        return userAuthState
    }

    suspend fun getAllUsers() {
        val users = userdoa.getAllFriends()
        val templist = users?.filter { user -> user.uid != mAuth.uid } as ArrayList<User>
        val friendList:MutableList<Friend> = ArrayList()
        for(user in templist) {
            val message = user.uid?.let { fetchLastMessageOfFriend(it) }
            val friend = Friend(user = user,lastMessage = message?.messagetxt, lastMessageSenderId = message?.userId)
            friendList.add(friend)
        }

        _allUsers.postValue(friendList)

    }

    private suspend fun fetchLastMessageOfFriend(friendId:String) : Message? {
        val roomID = mAuth.uid + friendId
        val messages:MutableList<Message> = messageDao.getAllMessage(roomID)
        return if(messages.isNotEmpty()) {
            messages[messages.size-1]
        } else {
            null
        }
    }

    private suspend fun checkRequestSent(currentUser:User, user2:User):Boolean {
        val lst1:List<FriendRequest> = friendRequestDao.getAllSentFriendRequest(currentUser,user2)
        Log.d("statuscheck", "lst1: ${lst1.toString()}")
        if (lst1.isNotEmpty()) return true
        val lst2:List<FriendRequest> = friendRequestDao.getAllSentFriendRequest(user2,currentUser)
        Log.d("statuscheck", "lst2: ${lst2.toString()}")
        return lst1.isNotEmpty() || lst2.isNotEmpty()
    }
    suspend fun getAllUserProfile() {
//        _allUserProfile.postValue(userdoa.getAllUser())
        var userLst : List<User> = ArrayList()
        val allUser = userdoa.getAllUser()
        val currentUser = userdoa.getCurrentUser()
        userLst = allUser.filter { it.uid != mAuth.uid } as ArrayList<User>
        userLst = allUser.filter { user -> !checkRequestSent(currentUser!!,user) } as ArrayList<User>
        _allUserProfile.postValue(userLst)
    }



}