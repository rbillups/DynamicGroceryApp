package com.example.dynamicgroceryapp.ui.appMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dynamicgroceryapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class profileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        //Stores current users UID in string
        val uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("UserTest")
        database.child(uid).get().addOnSuccessListener {
            val username = it.child("username").value
            val email = it.child("email").value
            val phone = it.child("phoneNum").value
            val image = it.child("imageUrl").value

            binding.tfUsername.hint = username.toString()
            binding.tfEmail.hint = email.toString()
            binding.tfPhone.hint = phone.toString()

            Glide.with(this).load(image).into(binding.profilePictureImage)
        }

        binding.logoutBtn.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }


}