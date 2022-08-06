package com.example.letschat.ui.Authentication.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterUserViewModel(application : Application) : AndroidViewModel(application) {

    private val userRepository : UserRepository = UserRepository()

    fun getAuthStatus(): MutableLiveData<Boolean> {
        return userRepository.getUserAuthState()
    }

    fun registerUser(firstname:String, lastname:String,img:Uri,type:String, email:String, password:String) {
        viewModelScope.launch {
            userRepository.registerUser(firstname,lastname,img, type,email,password)
        }
    }


}