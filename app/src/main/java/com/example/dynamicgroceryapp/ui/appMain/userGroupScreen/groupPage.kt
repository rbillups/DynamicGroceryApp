package com.example.dynamicgroceryapp.ui.appMain.userGroupScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding
import com.example.dynamicgroceryapp.databinding.FragmentGroupPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView



/**
 * A simple [Fragment] subclass.
 * Use the [groupPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class groupPage : Fragment() {


    private lateinit var binding: FragmentGroupPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentGroupPageBinding.inflate(inflater, container, false)
        val bottomNavigationView: BottomNavigationView = binding.groupBottomNav

        //bottom nav on click
        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            //
            when (menuItem.itemId) {

                //go to list fragment
                R.id.List ->{
                    val listFrag = userGroup.newInstance()
                    replaceFragment(listFrag)

                    true
                }
                //go to members frag
                R.id.Members -> {
                    val membersFrag = groupMemberPage.newInstance()
                    replaceFragment(membersFrag)
                    true
                }
                else->false
            }

        }



        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Replace the current fragment with the home fragment as the default
        val homeFragment = userGroup.newInstance()
        replaceFragment(homeFragment)
    }

    //change fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}