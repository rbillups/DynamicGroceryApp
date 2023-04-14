package com.example.dynamicgroceryapp.ui.appMain.userGroupScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.databinding.FragmentUserGroupBinding
import com.example.dynamicgroceryapp.ui.appMain.adapter.ItemAdapter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class userGroup : Fragment() {

    companion object {
        fun newInstance() = userGroup()
    }

    private lateinit var recycler: RecyclerView
    private lateinit var checklist: ArrayList<Item>
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: FragmentUserGroupBinding
    private lateinit var viewModel: UserGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentUser = FirebaseAuth.getInstance().currentUser

        //setup page
        binding = FragmentUserGroupBinding.inflate(inflater, container, false)
        checklist = ArrayList()
        val activity = requireActivity()


        //set up recycler view
        itemAdapter = ItemAdapter(activity, checklist)
        recycler = binding.recyclerView
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = itemAdapter

        //add item button
        binding.itemAdd.setOnClickListener {
            addItem()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserGroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun addItem() {
        //inflate popup screen
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(com.example.dynamicgroceryapp.R.layout.add_item, null)


        val addDialog = AlertDialog.Builder(requireContext())

        //pop up options
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->

            //database instance
            var reference = FirebaseDatabase.getInstance().getReference().child("Lists").push()

            //grab item name
            val name = v.findViewById<EditText>(com.example.dynamicgroceryapp.R.id.item_name)
            val newItem = Item(false, name.text.toString())

            //not done, storing item data
            val checkListId = reference.key


            //check if list already has the item
            if (!checklist.contains(newItem)) {
                checklist.add(newItem)
                itemAdapter.notifyDataSetChanged()
            }

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