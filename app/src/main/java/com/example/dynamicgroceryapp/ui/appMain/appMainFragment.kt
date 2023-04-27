package com.example.dynamicgroceryapp.ui.appMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding
import com.example.dynamicgroceryapp.ui.appMain.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class appMainFragment : Fragment() {

    //private val viewModel: appDashFragment by viewModels()
    private lateinit var binding: FragmentAppMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding set up
        binding= FragmentAppMainBinding.inflate(inflater, container, false)

        //viewpager adapter setup
        val viewPager: ViewPager2 = binding.viewPager2
        val bottomNavigationView: BottomNavigationView = binding.bottomNav

        //attach fragment activity to adapter
        val activity = requireActivity()
        val viewPagerAdapter = ViewPagerAdapter(activity)
        viewPager.adapter = viewPagerAdapter

        //bottom nav on click
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
               // R.id.listBottomNav -> viewPager.currentItem = 0
                R.id.groupBottomNav -> viewPager.currentItem = 0
                R.id.friendsBottomNav -> viewPager.currentItem = 1
                R.id.profileBottomNav -> viewPager.currentItem = 2
            }
            true
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

