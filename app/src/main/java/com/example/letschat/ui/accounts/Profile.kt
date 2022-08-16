package com.example.letschat.ui.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.ui.accounts.adapter.FriendRequestAdapter
import com.example.letschat.ui.accounts.viewmodel.ProfileViewModel


class Profile : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendRequestAdapter: FriendRequestAdapter
    private val profileViewModel : ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = view.findViewById(R.id.friend_request_recyclerview)
        friendRequestAdapter = FriendRequestAdapter(requireContext())

        profileViewModel.allFriendRequests.observe(viewLifecycleOwner) {
            it?.let {
                friendRequestAdapter.updateUserList(it)
            }
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = friendRequestAdapter
        }
        return view
    }


}