package com.example.dynamicgroceryapp.ui.appMain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dynamicgroceryapp.databinding.UserProfileLayoutBinding
import com.example.dynamicgroceryapp.model.UserModel

class ShowAllUserAdapter(val context : Context, val list : ArrayList<UserModel>) : RecyclerView.Adapter<ShowAllUserAdapter.ShowAllUserViewHolder>(){
    inner class ShowAllUserViewHolder(val binding: UserProfileLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAllUserViewHolder {
        return ShowAllUserViewHolder(UserProfileLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowAllUserViewHolder, position: Int) {
        Glide.with(context).load(list[position].image).into(holder.binding.userImage)

        holder.binding.userName.text = list[position].name
    }
}