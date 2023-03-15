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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentAppMainBinding
import com.example.dynamicgroceryapp.databinding.FragmentGroupsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.malkinfo.editingrecyclerview.view.UserAdapter

class groupsFragment : Fragment() {

    companion object {
        fun newInstance() = groupsFragment()
    }

    private lateinit var viewModel: GroupsViewModel
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<Group>
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: FragmentGroupsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGroupsBinding.inflate(inflater, container, false)
        userList=ArrayList()
        val activity = requireActivity()
        userAdapter= UserAdapter(activity,userList)

        recv=binding.mRecycler

        recv.layoutManager= LinearLayoutManager(requireContext())

        recv.adapter=userAdapter

        binding.addingBtn.setOnClickListener{addGroup()

        }

       return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun addGroup(){
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.add_group,null)
        /**set view*/
        val groupName = v.findViewById<EditText>(R.id.addGroupName)
        val groupDesc = v.findViewById<EditText>(R.id.descriptionOfGroup)


        val addDialog = AlertDialog.Builder(requireContext())

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = groupName.text.toString()
            val descrip = groupDesc.text.toString()
            userList.add(Group("Name: $names","Description: $descrip"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(),"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(requireContext(),"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()

    }

}