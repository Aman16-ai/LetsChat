package com.example.letschat.ui.Authentication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letschat.data.repository.UserRepository

class LogoutViewModel: ViewModel() {

    private val userAuthenticationRepository = UserRepository()
//    val authStatus : LiveData<Boolean> = userAuthenticationRepository.getUserAuthState()
    fun logout() {
        userAuthenticationRepository.logoutUser()
    }
    fun getAuthState() : MutableLiveData<Boolean> {
        return userAuthenticationRepository.getUserAuthState()
    }
}