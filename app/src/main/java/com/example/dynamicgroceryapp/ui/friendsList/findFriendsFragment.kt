package com.example.dynamicgroceryapp.ui.friendsList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dynamicgroceryapp.R

class findFriendsFragment : Fragment() {

    companion object {
        fun newInstance() = findFriendsFragment()
    }

    private lateinit var viewModel: FindFriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_friends, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindFriendsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}