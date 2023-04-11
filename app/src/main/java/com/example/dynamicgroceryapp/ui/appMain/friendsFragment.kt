package com.example.dynamicgroceryapp.ui.appMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding
import com.example.dynamicgroceryapp.databinding.FragmentFriendsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.checkerframework.common.subtyping.qual.Bottom

class friendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding

    companion object {
        fun newInstance() = friendsFragment()
    }

    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding set up
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        //viewpager adapter setup
        val viewPager: ViewPager2 = binding.viewPagerFriends
        val topNavigationView: BottomNavigationView = binding.friendsTopnavbar

        //attach fragment activity to adapter
        val activity = requireActivity()
        val viewPagerAdapter = ViewPagerFriendsAdapter(activity)
        viewPager.adapter = viewPagerAdapter

        //Top nav on click listener
        topNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.friends -> viewPager.currentItem = 0
                R.id.requests -> viewPager.currentItem = 1
                R.id.find_friends -> viewPager.currentItem = 2

            }
            true
        }





        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)
        // TODO: Use the ViewModel
    }



}