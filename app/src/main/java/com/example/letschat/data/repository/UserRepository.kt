package com.example.letschat.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.dao.UserDao
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

    private var _allUsers:MutableLiveData<List<User>> = MutableLiveData()
    val allUser : LiveData<List<User>>
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
                val imgUrl = withContext(Dispatchers.IO) {
                    uploadProfileImg(imgUri, imgtype)
                }
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
        val users = userdoa.getAllUser()
        val templist = users.filter { user -> user.uid != mAuth.uid } as ArrayList<User>
        _allUsers.postValue(templist)
    }
}