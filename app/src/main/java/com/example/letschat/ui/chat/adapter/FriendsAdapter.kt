package com.example.letschat.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.data.model.Friend
import com.example.letschat.data.model.Message
import com.example.letschat.data.model.User
import com.example.letschat.ui.chat.FriendsFragmentDirections
import com.example.letschat.ui.chat.viewmodel.FriendsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class FriendsAdapter(val context: Context) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    private var friendsList = ArrayList<Friend>()
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt : TextView = itemView.findViewById(R.id.friend_txt)
        val lasttxt : TextView = itemView.findViewById(R.id.last_message_tv)
        val img : ImageView = itemView.findViewById(R.id.user_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.friends_list,parent,false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.txt.text = "${friendsList[position].user?.firstName} ${friendsList[position].user?.lastName}"
        Glide.with(context).load(friendsList[position].user?.profileImg?:R.drawable.ic_baseline_person_24).into(holder.img)

        mAuth.uid?.let { currentUserID ->
            if(currentUserID == friendsList[position].lastMessageSenderId) {
                holder.lasttxt.text = "You : ${friendsList[position].lastMessage}"
            }
            else {
                holder.lasttxt.text = friendsList[position].lastMessage
            }
        }
        holder.itemView.setOnClickListener {
            val action = FriendsFragmentDirections.actionFriendsFragmentToChatFragment(friendsList[position])
            it.findNavController().navigate(action)
        }
    }

    fun updateFriends(updatedList:List<Friend>) {
        friendsList.clear()
        friendsList.addAll(updatedList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return friendsList.size
    }


}