package com.example.dynamicgroceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dynamicgroceryapp.ui.addfriend.AddFriendsFragment

class AddFriends : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddFriendsFragment.newInstance())
                .commitNow()
        }
    }
}