package com.example.letschat.utils

import com.example.letschat.data.model.FriendRequest
import com.example.letschat.ui.accounts.adapter.FriendRequestAdapter

interface ResponseCallBack {
    fun callback(holder: FriendRequestAdapter.FriendRequestAdapter, friendRequest: FriendRequest)
}