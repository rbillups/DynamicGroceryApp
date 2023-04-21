package com.example.dynamicgroceryapp.ui.friendsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.databinding.FragmentFriendRequestBinding
import com.example.dynamicgroceryapp.ui.appMain.adapter.FriendRequestUserListAdapter
import com.example.dynamicgroceryapp.ui.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendRequestFragment : Fragment() {
    private lateinit var binding: FragmentFriendRequestBinding
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var userAdapter: FriendRequestUserListAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = FriendRequestFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Initialize variables
        binding = FragmentFriendRequestBinding.inflate(inflater, container, false)
        userList = ArrayList()
        val activity = requireActivity()

        //Set up Recycler View
        userAdapter = FriendRequestUserListAdapter(activity, userList)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userAdapter

        //Populate Friend Requests List
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            val userRequestReference =
                FirebaseDatabase.getInstance().getReference("UserTest/$userId/Requests")

            userRequestReference.addValueEventListener(object : ValueEventListener {

                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (requestSnapshot in snapshot.children) {
                        val usersId = requestSnapshot.key
                        val username = requestSnapshot.child("username").value
                        val imageURL = requestSnapshot.child("imageUrl").value
                        userList.add(
                            UserModel(
                                "$username",
                                "$imageURL",
                                "$usersId"
                            )
                        )

                    }
                    userAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }



        return binding.root
    }
}