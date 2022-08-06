package com.example.letschat.data.dao

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserDao {
    private var db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()
    suspend fun getUserDetails(): User? {
        return mAuth.uid?.let {
            db.collection("user").document(it).get().await().toObject(User::class.java)
        }
    }

    suspend fun getAllUser() : List<User> {
        return db.collection("user").get().await().toObjects(User::class.java)
    }
    suspend fun saveUserProfileImgFile(profileUri:Uri,type:String):String? {
        val storage = FirebaseStorage.getInstance().reference.child("ProfileImg/").child("${System.currentTimeMillis()}.${type}")
        val result = storage.putFile(profileUri).await()
        if(result.task.isSuccessful) {
            return storage.downloadUrl.await().toString()
        }
        return null
    }
    suspend fun saveUserDetails(user: User):Boolean {
        return try {
            var result = mAuth.uid?.let {
                db.collection("user")
                    .document(it).set(user).await()
            }
            result != null
        }
        catch(e:Exception) {
            e.printStackTrace()
            false
        }
    }

}