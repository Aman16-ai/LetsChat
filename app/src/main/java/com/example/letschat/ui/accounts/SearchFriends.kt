package com.example.letschat.ui.accounts

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.R
import com.example.letschat.ui.accounts.adapter.AllUserProfileAdapter
import com.example.letschat.ui.accounts.viewmodel.FriendRequestViewModel


class SearchFriends : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userProfileAdapter: AllUserProfileAdapter
    private lateinit var searchView:SearchView

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_friend_fragment_menu,menu)
        val searchItem = menu.findItem(R.id.search_friend_item)
        searchView = searchItem.actionView as SearchView
    }


}