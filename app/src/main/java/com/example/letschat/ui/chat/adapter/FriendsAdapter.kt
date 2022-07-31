package com.example.letschat.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.data.model.User
import com.example.letschat.ui.chat.FriendsFragmentDirections
import org.w3c.dom.Text


class FriendsAdapter(val context: Context) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    private var friendsList = ArrayList<User>()
    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt : TextView = itemView.findViewById(R.id.friend_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.friends_list,parent,false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
       holder.txt.text = "${friendsList[position].firstName} ${friendsList[position].lastName}"
        holder.itemView.setOnClickListener {
            val action = FriendsFragmentDirections.actionFriendsFragmentToChatFragment(friendsList[position])
            it.findNavController().navigate(action)
        }
    }

    fun updateFriends(updatedList:List<User>) {
        friendsList.clear()
        friendsList.addAll(updatedList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return friendsList.size
    }


}