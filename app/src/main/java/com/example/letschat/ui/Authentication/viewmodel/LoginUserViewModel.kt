package com.example.letschat.ui.Authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginUserViewModel(application: Application) : AndroidViewModel(application) {
    private val authrepo = UserRepository()
    fun getAuthStatus() : MutableLiveData<Boolean> {
        return authrepo.getUserAuthState()
    }
    fun loginUser(email:String,password:String) {
        viewModelScope.launch {
            authrepo.loginUser(email,password)
        }
    }
}