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
    suspend fun getCurrentUser(): User? {
        return mAuth.uid?.let {
            db.collection("user").document(it).get().await().toObject(User::class.java)
        }
    }

    suspend fun getAllUser() : List<User> {
        return db.collection("user").get().await().toObjects(User::class.java)
    }

    suspend fun getAllFriends():List<User>? {
        return try {
            db.collection("user")
                .document(mAuth.uid!!)
                .collection("friends")
                .get()
                .await()
                .toObjects(User::class.java)
        }
        catch(e:Exception) {
            e.printStackTrace()
            null
        }
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

    suspend fun addFriend(userId: String, userFriend:User):Boolean{
        return try {
            var result = db
                .collection("user")
                .document(userId)
                .collection("friends")
                .add(userFriend)
                .await()
            return true
        }
        catch(e:Exception) {
            e.printStackTrace()
            false
        }
    }

}