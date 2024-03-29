package com.malkinfo.editingrecyclerview.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.ui.appMain.Group


class UserAdapter(val c:Context,val userList:ArrayList<Group>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    private var listener: OnItemClickListener? = null

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var groupName= v.findViewById<TextView>(R.id.groupName)

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.group_item,parent,false)


        return UserViewHolder(v)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.groupName.text = newList.name

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return  userList.size
    }
}