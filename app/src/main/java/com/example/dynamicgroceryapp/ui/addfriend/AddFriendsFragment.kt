package com.example.dynamicgroceryapp.ui.addfriend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dynamicgroceryapp.R

class AddFriendsFragment : Fragment() {

    companion object {
        fun newInstance() = AddFriendsFragment()
    }

    private lateinit var viewModel: AddFriend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddFriend::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_addfriend, container, false)
    }

}