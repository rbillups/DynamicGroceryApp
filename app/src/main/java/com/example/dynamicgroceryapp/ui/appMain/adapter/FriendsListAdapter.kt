package com.example.dynamicgroceryapp.ui.appMain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.ui.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FriendsListAdapter(var context: Context, var list: ArrayList<UserModel>) :
    RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {
    private lateinit var friendReference: DatabaseReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView = itemView.findViewById(R.id.usernameplaceholder)
        var image: ImageView = itemView.findViewById(R.id.profilepicture)
        var deleteButton: Button = itemView.findViewById(R.id.deletebutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_layout_for_friends_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Initialize database and current user
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("UserTest")
        friendReference = databaseReference.child(mUser).child("Friends")

        //Sets text as their username from list
        holder.username.text = list[position].username

        //Sets image corresponding to user
        Glide.with(context).load(list[position].profileImage)
            .placeholder(R.drawable.pfp_placeholder)
            .into(holder.image)

        //Click listener for delete button
        holder.deleteButton.setOnClickListener {
            //Removes friend from current users friends list
            friendReference.child(list[position].UID).removeValue()
            //Removes friend from others friends list
            databaseReference.child(list[position].UID).child("Friends").child(mUser).removeValue()

            Toast.makeText(context, "User has been removed", Toast.LENGTH_SHORT).show()
        }
    }
}