package com.example.letschat.data.dao

import androidx.lifecycle.MutableLiveData
import com.example.letschat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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