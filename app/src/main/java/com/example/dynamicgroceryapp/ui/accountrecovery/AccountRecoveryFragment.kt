package com.example.dynamicgroceryapp.ui.accountrecovery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dynamicgroceryapp.R

class AccountRecoveryFragment : Fragment() {

    companion object {
        fun newInstance() = AccountRecoveryFragment()
    }

    private lateinit var viewModel: AccountRecovery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountRecovery::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recovery, container, false)
    }

}