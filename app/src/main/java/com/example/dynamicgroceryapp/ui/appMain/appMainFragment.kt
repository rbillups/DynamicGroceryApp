package com.example.dynamicgroceryapp.ui.appMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding


class appDashFragment : Fragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_main, container, false)
    }
}

