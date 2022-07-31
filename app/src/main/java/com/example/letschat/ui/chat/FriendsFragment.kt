package com.example.letschat.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.ui.chat.adapter.FriendsAdapter
import com.example.letschat.ui.chat.viewmodel.FriendsViewModel


class FriendsFragment : Fragment() {


    private val friendsViewModel : FriendsViewModel by viewModels()
    private lateinit var recyclerview: RecyclerView
    private lateinit var friendsAdapter: FriendsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        recyclerview = view.findViewById(R.id.friends_recyclerview)
        friendsAdapter = FriendsAdapter(requireContext())
        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = friendsAdapter
        }

        friendsViewModel.allUsers.observe(viewLifecycleOwner) {
            it?.let {
                friendsAdapter.updateFriends(it)
            }
        }
        return view
    }


}