package com.example.dynamicgroceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dynamicgroceryapp.ui.signup.SignUpFragment

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignUpFragment.newInstance())
                .commitNow()
        }
    }
}