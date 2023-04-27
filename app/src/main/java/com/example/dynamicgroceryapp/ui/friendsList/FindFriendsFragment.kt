package com.example.dynamicgroceryapp.ui.friendsList

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dynamicgroceryapp.databinding.FragmentFindFriendsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FindFriendsFragment : Fragment() {
    private lateinit var binding: FragmentFindFriendsBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var requestReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var username: String
    private lateinit var imageURL: String

    companion object {
        fun newInstance() = FindFriendsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindFriendsBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("UserTest")

        //Populate Username, Image Link, and UID
        databaseReference.child(mUser).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                username = snapshot.child("username").value.toString()
                imageURL = snapshot.child("imageUrl").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "ERROR", error.toException())
            }
        })

        //Listener for friend request button
        binding.sendFriendReqBtn.setOnClickListener {
            val email = binding.tfSearchFriend.text.toString()
            var searchID: String
            var isFriends = false


            //Checks if email is a valid user in database and if so sends that user a friend request
            databaseReference.orderByChild("email").equalTo(email)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {
                            Toast.makeText(activity, "User not found", Toast.LENGTH_SHORT).show()
                        }
                        for (postSnapshot in snapshot.children) {
                            searchID = postSnapshot.key.toString()
                            requestReference =
                                FirebaseDatabase.getInstance().getReference("UserTest")

                            //Checks if user is already friends
                            requestReference.child(mUser).child("Friends").child(searchID)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            isFriends = true
                                            Toast.makeText(
                                                activity,
                                                "User is Already Added!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        //Continues with sending the specified email a friend request
                                        else {
                                            val hashMap: MutableMap<String, String> = HashMap()
                                            hashMap["username"] = username
                                            hashMap["imageUrl"] = imageURL
                                            if (!isFriends) {
                                                if (searchID != mUser) {
                                                    requestReference.child(searchID)
                                                        .child("Requests").child(mUser)
                                                        .updateChildren(hashMap as Map<String, Any>)
                                                        .addOnCompleteListener(requireActivity()) { task ->
                                                            if (task.isSuccessful) {
                                                                //Clears textfield after friend request sent and shows a confirmation message
                                                                Toast.makeText(
                                                                    activity,
                                                                    "Friend Request Sent!",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                binding.tfSearchFriend.onEditorAction(
                                                                    EditorInfo.IME_ACTION_DONE
                                                                )
                                                                binding.tfSearchFriend.text.clear()
                                                            }

                                                        }
                                                } else {
                                                    Toast.makeText(
                                                        activity,
                                                        "Error, Try Again!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.w(TAG, "ERROR", error.toException())
                                    }
                                })
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "ERROR", error.toException())
                    }
                })
        }
        return binding.root
    }
}

