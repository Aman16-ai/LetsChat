package com.example.letschat.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.data.model.Message
import com.example.letschat.helper.getDateTime
import com.example.letschat.utils.Chat
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mAuth = FirebaseAuth.getInstance()
    object MessageType {
        const val SENDER_MESSAGE = 10
        const val RECEIVER_MESSAGE = 20
    }
    val messages = ArrayList<Message>()
    val chat = ArrayList<Chat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if(viewType == 30) {
//            val inflater = LayoutInflater.from(context)
//            val view = inflater.inflate(R.layout.chat_timestamp,parent,false)
//            ChatTimeViewHolder(view)
//        }
        return if(viewType == MessageType.SENDER_MESSAGE) {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.send_message_box,parent,false)
            SendMessageViewHolder(view)
        } else {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.receive_message_box,parent,false)
            RecieveMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if(getItemViewType(position) == 30) {
//            holder as ChatTimeViewHolder
//            val c : Chat.time = chat[position] as Chat.time
//            holder.tv.text = c.timeStamp?.toDate().toString()
//        }
         if(getItemViewType(position) == MessageType.SENDER_MESSAGE) {
            holder as SendMessageViewHolder
            holder.sendTxt.text = messages[position].messagetxt
            holder.timeTxt.text = messages[position].timestamp?.toDate()?.toString()
                ?.let { getDateTime(it) }

//            val c : Chat.content = chat[position] as Chat.content
//            holder.sendTxt.text = c.message?.messagetxt
//            holder.timeTxt.text = getDateTime(c.message?.timestamp?.toDate().toString())
        }
        else {
            holder as RecieveMessageViewHolder
            holder.recTxt.text = messages[position].messagetxt
            holder.timeTxt.text = messages[position].timestamp?.toDate()?.toString()
                ?.let { getDateTime(it) }
//            val c : Chat.content = chat[position] as Chat.content
//            holder.recTxt.text = c.message?.messagetxt
//            holder.timeTxt.text = getDateTime(c.message?.timestamp?.toDate().toString())
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
    fun updateMessages(newMessages:List<Message>) {
        messages.clear()
        messages.addAll(newMessages)
//        chat.clear()
//        chat.addAll(newMessages)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(messages[position].userId == mAuth.uid) MessageType.SENDER_MESSAGE
        else MessageType.RECEIVER_MESSAGE
//        val c = chat[position]
//        return when(c) {
//            is Chat.content -> {
//                if(c.message?.userId == mAuth.uid) MessageType.SENDER_MESSAGE
//                else MessageType.RECEIVER_MESSAGE
//            }
//            is Chat.time -> {
//                30
//            }
//        }
    }
    inner class SendMessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val sendTxt : TextView = itemView.findViewById(R.id.send_message_txt)
        val timeTxt : TextView = itemView.findViewById(R.id.send_message_timetv)
    }
    inner class RecieveMessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val recTxt : TextView = itemView.findViewById(R.id.recieve_message_txt)
        val timeTxt : TextView = itemView.findViewById(R.id.receive_message_timetv)
    }

    inner class ChatTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.chat_time_tv)
    }
}