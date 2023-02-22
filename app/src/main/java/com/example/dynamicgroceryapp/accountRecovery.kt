package com.example.dynamicgroceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dynamicgroceryapp.ui.accountrecovery.AccountRecoveryFragment

class accountRecovery : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AccountRecoveryFragment.newInstance())
                .commitNow()
        }
    }
}