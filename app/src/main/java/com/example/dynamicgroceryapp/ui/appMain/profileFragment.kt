package com.example.dynamicgroceryapp.ui.appMain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dynamicgroceryapp.MainActivity
import com.example.dynamicgroceryapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class profileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        //Stores current users UID in string
        val uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("UserTest")

        //Grabs current users data and displays them
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

        //Listener for logout button
        binding.logoutBtn.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    //Function that signs the current logged in user out and sends back to login page
    private fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(context, MainActivity::class.java))
        Toast.makeText(context, "Signed Out", Toast.LENGTH_SHORT).show()
    }


}