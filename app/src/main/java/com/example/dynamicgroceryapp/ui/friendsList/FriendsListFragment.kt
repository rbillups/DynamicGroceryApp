package com.example.dynamicgroceryapp.ui.friendsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.databinding.FragmentFriendslistBinding
import com.example.dynamicgroceryapp.ui.appMain.adapter.FriendsListAdapter
import com.example.dynamicgroceryapp.ui.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendsListFragment : Fragment() {
    private lateinit var binding: FragmentFriendslistBinding
    private lateinit var friendsList: ArrayList<UserModel>
    private lateinit var userAdapter: FriendsListAdapter
    private lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = FriendsListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Initialize variable
        binding = FragmentFriendslistBinding.inflate(inflater, container, false)
        friendsList = ArrayList()
        val activity = requireActivity()

        //Set up Recycler View
        userAdapter = FriendsListAdapter(activity, friendsList)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userAdapter

        //Populate Friends List
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userID = currentUser.uid
            val friendsListReference =
                FirebaseDatabase.getInstance().getReference("UserTest/$userID/Friends")

            friendsListReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    friendsList.clear()
                    for (friendSnapshot in snapshot.children) {

                        val friendID = friendSnapshot.key
                        val username = friendSnapshot.child("username").value
                        val imageURL = friendSnapshot.child("imageUrl").value

                        friendsList.add(
                            UserModel(
                                "$username",
                                "$imageURL",
                                "$friendID"
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