package com.example.dynamicgroceryapp.ui.appMain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dynamicgroceryapp.ui.friendsList.findFriendsFragment
import com.example.dynamicgroceryapp.ui.friendsList.friendRequestFragment
import com.example.dynamicgroceryapp.ui.friendsList.friendslistFragment

class ViewPagerFriendsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: List<Fragment> = listOf(
        friendslistFragment(),
        friendRequestFragment(),
        findFriendsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}