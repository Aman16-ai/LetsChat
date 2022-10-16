package com.example.letschat.data.dao

import com.example.letschat.constants.FriendRequestStatus
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FriendRequestDao {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val userDao = UserDao()

    suspend fun getAllFriendRequest(currentUser: User):List<FriendRequest> {
        return db.collection("Friend_Request")
            .whereEqualTo("receiverUser",currentUser)
            .whereEqualTo("status",FriendRequestStatus.SENT)
            .get()
            .await()
            .toObjects(FriendRequest::class.java)
    }

    suspend fun sendFriendRequest(friendRequest: FriendRequest):Boolean {
        return try {
            val docRef = db.collection("Friend_Request")
                .add(friendRequest)
                .await()
            docRef.update("id",docRef.id).await()
            true
        } catch(err:Exception) {
            err.printStackTrace()
            false
        }
    }

    suspend fun updateFriendRequestStatus(friendRequest: FriendRequest):Boolean {
        return try {
            friendRequest.id?.let {
                db.collection("Friend_Request")
                    .document(it)
                    .update("status",FriendRequestStatus.ACCEPTED)
                    .await()
            }
             true
        }
        catch (e:Exception) {
            e.printStackTrace()
            false
        }
    }
}