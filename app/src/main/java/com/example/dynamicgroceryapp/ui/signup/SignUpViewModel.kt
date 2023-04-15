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
data class User(val username: String ?= null, var password: String?= null, var firstName : String ?= null, var email : String ?= null, var phoneNum : String ?= null, var imageUrl : String ?= null)