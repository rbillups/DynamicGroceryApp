package com.example.dynamicgroceryapp.ui.appMain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dynamicgroceryapp.ui.appMain.friendsFragment
import com.example.dynamicgroceryapp.ui.appMain.groupsFragment
import com.example.dynamicgroceryapp.ui.appMain.listFragment
import com.example.dynamicgroceryapp.ui.appMain.profileFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: List<Fragment> = listOf(
        listFragment(),
        groupsFragment(),
        friendsFragment(),
        profileFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}