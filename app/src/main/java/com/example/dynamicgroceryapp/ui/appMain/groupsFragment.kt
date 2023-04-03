package com.example.dynamicgroceryapp.ui.appMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding
import com.example.dynamicgroceryapp.databinding.FragmentGroupsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.malkinfo.editingrecyclerview.view.UserAdapter

class groupsFragment : Fragment() {

    companion object {
        fun newInstance() = groupsFragment()
    }

    //private lateinit var viewModel: GroupsViewModel
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<Group>
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: FragmentGroupsBinding
    //private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //create variable
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        userList = ArrayList()
        val activity = requireActivity()

        //set up recycler view
        userAdapter = UserAdapter(activity, userList)
        recv = binding.mRecycler
        recv.layoutManager = LinearLayoutManager(requireContext())
        recv.adapter = userAdapter

        //populate recycler view with data if data is there
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            //grab groups data reference
            val Ref = FirebaseDatabase.getInstance().getReference("UserTest/$userId/groupIds")

            //loop through node and grab data
            Ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (groupSnapshot in dataSnapshot.children) {
                        val groupId = groupSnapshot.key

                        // Get the data for this group from the "groups" node
                        val groupRef =
                            FirebaseDatabase.getInstance().getReference("groups/$groupId")
                        groupRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(groupDataSnapshot: DataSnapshot) {
                                // Get the group data and do something with it
                                val groupName =
                                    groupDataSnapshot.child("name").getValue(String::class.java)
                                val groupDescription = groupDataSnapshot.child("description")
                                    .getValue(String::class.java)

                                // populate recycler view with group data
                                userList.add(
                                    Group(
                                        "Name: $groupName",
                                        "Description: $groupDescription"
                                    )
                                )
                                userAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle the error...
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error...
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }

        //add group
        binding.addingBtn.setOnClickListener {
            addGroup()

        }

        //make recycler view clickable
        userAdapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {


            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  viewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private fun addGroup() {
        //inflate popup screen
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.add_group, null)

        //create variables
        val groupName = v.findViewById<EditText>(R.id.addGroupName)
        val groupDesc = v.findViewById<EditText>(R.id.descriptionOfGroup)
        val addDialog = AlertDialog.Builder(requireContext())

        //pop up options
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val name = groupName.text.toString()
            val descrip = groupDesc.text.toString()

            //database instance
            var reference = FirebaseDatabase.getInstance().getReference().child("groups").push()
            val newGroup = Group(name, descrip)
            val groupId = reference.key

            // Add the current user to the group
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val userId = currentUser.uid

                //grab username ref
                val usernameRef =
                    FirebaseDatabase.getInstance().getReference("UserTest/$userId/userName")

                //update database
                usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //grab username
                        val username = dataSnapshot.getValue(String::class.java)

                        //add group to database
                        reference.setValue(newGroup)

                        //add current user to group
                        if (username != null) {
                            reference.child("members").child(username).setValue(true)
                        }
                        //Add group id to the users id in database
                        if (groupId != null) {
                            FirebaseDatabase.getInstance().getReference("UserTest/$userId/groupIds")
                                .child(groupId).setValue(true)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                })


            }

            //update recycler view
            userList.add(Group("Name: $name", "Description: $descrip"))
            userAdapter.notifyDataSetChanged()

            dialog.dismiss()
        }
        //cancel option
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()

    }


}