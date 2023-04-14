package com.example.dynamicgroceryapp.ui.appMain.userGroupScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dynamicgroceryapp.R

class groupMemberPage : Fragment() {

    companion object {
        fun newInstance() = groupMemberPage()
    }

    private lateinit var viewModel: GroupMemberPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_member_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupMemberPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}