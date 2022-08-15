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
import com.example.letschat.ui.accounts.adapter.AllUserProfileAdapter
import com.example.letschat.ui.accounts.viewmodel.FriendRequestViewModel


class SearchFriends : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userProfileAdapter: AllUserProfileAdapter

    private val friendRequestViewModel : FriendRequestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_friends, container, false)
        recyclerView = view.findViewById(R.id.all_user_profile_recyclerView)

        friendRequestViewModel.allUserProfile.observe(viewLifecycleOwner) {
            it?.let {
                userProfileAdapter.updateUserList(it)
            }
        }

        userProfileAdapter = AllUserProfileAdapter(requireContext(),friendRequestViewModel)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userProfileAdapter
        }


        return view
    }


}