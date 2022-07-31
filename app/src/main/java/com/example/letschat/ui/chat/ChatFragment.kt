package com.example.letschat.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.data.model.Message
import com.example.letschat.ui.Authentication.viewmodel.LogoutViewModel
import com.example.letschat.ui.chat.adapter.ChatAdapter
import com.example.letschat.ui.chat.adapter.FriendsAdapter
import com.example.letschat.ui.chat.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {


    private lateinit var btn :Button
    private lateinit var et:EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private val chatViewModel : ChatViewModel by viewModels()

    private val navArgs : ChatFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_chat, container, false)

        val friendUserId = navArgs.user.uid
        chatAdapter = ChatAdapter(requireContext())

        chatViewModel.getAllMessages(friendUserId!!).observe(viewLifecycleOwner) {
            it?.let {
                for(i in it) {
                    Log.d("messge", "onCreateView: "+i.messagetxt)
                }
                chatAdapter.updateMessages(it.sortedBy { i -> i.timestamp.seconds })
            }
        }
        btn = view.findViewById(R.id.sendBtn)
        et = view.findViewById(R.id.message_et)
        recyclerView = view.findViewById(R.id.chat_recyclerview)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
        btn.setOnClickListener {
            chatViewModel.sendMessage(et.text.toString(),friendUserId)
        }
        return view
    }


}