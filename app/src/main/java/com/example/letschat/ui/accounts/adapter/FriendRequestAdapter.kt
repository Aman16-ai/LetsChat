package com.example.letschat.ui.accounts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.data.model.Friend
import com.example.letschat.data.model.FriendRequest
import com.example.letschat.data.model.User
import com.example.letschat.utils.toast

class FriendRequestAdapter(val context:Context):RecyclerView.Adapter<FriendRequestAdapter.FriendRequestAdapter>() {

    private val allFriendRequestUser:MutableList<FriendRequest> = ArrayList()
    inner class FriendRequestAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userProfileImageView : ImageView = itemView.findViewById(R.id.user_req_profile_img)
        val userNameTextView : TextView = itemView.findViewById(R.id.user_req_name_txt)
        val btn : Button = itemView.findViewById(R.id.accept_req_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestAdapter {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.friend_request_list,parent,false)
        return FriendRequestAdapter(view)
    }

    override fun onBindViewHolder(holder: FriendRequestAdapter, position: Int) {
        val senderUser = allFriendRequestUser[position].senderUser
        Glide.with(context).load(senderUser?.profileImg?: R.drawable.ic_baseline_person_24).into(holder.userProfileImageView)
        holder.userNameTextView.text = "${senderUser?.firstName} ${senderUser?.lastName}"

//        holder.btn.setOnClickListener {
//            context.toast("sending friend request")
//            friendRequestViewModel.sendFriendRequest(user)
//        }
    }

    fun updateUserList(newUserList:List<FriendRequest>) {
        allFriendRequestUser.clear()
        allFriendRequestUser.addAll(newUserList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return allFriendRequestUser.size
    }
}