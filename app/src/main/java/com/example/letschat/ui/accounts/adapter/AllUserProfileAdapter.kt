package com.example.letschat.ui.accounts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.data.model.User
import com.example.letschat.ui.accounts.viewmodel.FriendRequestViewModel
import com.example.letschat.utils.toast


class AllUserProfileAdapter(val context: Context,val friendRequestViewModel: FriendRequestViewModel) : RecyclerView.Adapter<AllUserProfileAdapter.AllUserProfileViewHolder>() {

    private val allUserList:MutableList<User> = ArrayList()
    inner class AllUserProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userProfileImageView : ImageView = itemView.findViewById(R.id.user_profile_img)
        val userNameTextView : TextView = itemView.findViewById(R.id.user_name_txt)
        val btn :Button = itemView.findViewById(R.id.send_request_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserProfileViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.all_user_list,parent,false)
        return AllUserProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllUserProfileViewHolder, position: Int) {
        val user = allUserList[position]
        Glide.with(context).load(user.profileImg?:R.drawable.ic_baseline_person_24).into(holder.userProfileImageView)
        holder.userNameTextView.text = "${user.firstName} ${user.lastName}"

        holder.btn.setOnClickListener {
            context.toast("sending friend request")
            friendRequestViewModel.sendFriendRequest(user)
        }
    }

    fun updateUserList(newUserList:List<User>) {
        allUserList.clear()
        allUserList.addAll(newUserList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return allUserList.size
    }
}