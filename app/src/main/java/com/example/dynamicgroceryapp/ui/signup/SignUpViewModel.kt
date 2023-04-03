package com.example.dynamicgroceryapp.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val user = MutableLiveData<User>()

    fun setUser(userName: String) {
        user.value = User(userName)
    }
}
data class User(val userName: String)