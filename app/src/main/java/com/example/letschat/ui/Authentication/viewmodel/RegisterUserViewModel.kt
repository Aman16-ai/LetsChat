package com.example.letschat.ui.Authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.letschat.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterUserViewModel(application : Application) : AndroidViewModel(application) {

    private val authenticationRepository : UserRepository = UserRepository()

    fun getAuthStatus(): MutableLiveData<Boolean> {
        return authenticationRepository.getUserAuthState()
    }

    fun registerUser(firstname:String, lastname:String, email:String, password:String) {
        viewModelScope.launch {
            authenticationRepository.registerUser(firstname,lastname,email,password)
        }
    }


}