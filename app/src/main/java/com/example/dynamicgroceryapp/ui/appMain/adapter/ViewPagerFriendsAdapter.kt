package com.example.dynamicgroceryapp.ui.appMain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dynamicgroceryapp.ui.friendsList.FindFriendsFragment
import com.example.dynamicgroceryapp.ui.friendsList.FriendRequestFragment
import com.example.dynamicgroceryapp.ui.friendsList.FriendsListFragment

class ViewPagerFriendsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: List<Fragment> = listOf(
        FriendsListFragment(),
        FriendRequestFragment(),
        FindFriendsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}