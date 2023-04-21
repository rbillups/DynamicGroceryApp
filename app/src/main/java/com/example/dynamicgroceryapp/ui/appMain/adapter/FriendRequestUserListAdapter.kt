package com.example.dynamicgroceryapp.ui.appMain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.ui.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FriendRequestUserListAdapter(var context: Context, var list: ArrayList<UserModel>) :
    RecyclerView.Adapter<FriendRequestUserListAdapter.ViewHolder>() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView = itemView.findViewById(R.id.usernameplaceholder)
        var image: ImageView = itemView.findViewById(R.id.profilepicture)
        var acceptButton: Button = itemView.findViewById(R.id.acceptbutton)
        var rejectButton: Button = itemView.findViewById(R.id.rejectbutton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_layout_for_friend_request, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Initialize database and current user
        databaseReference = FirebaseDatabase.getInstance().getReference("UserTest")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser?.uid.toString()


        //Sets text as their username from list
        holder.username.text = list[position].username

        //Sets image corresponding to user
        Glide.with(context).load(list[position].profileImage)
            .placeholder(R.drawable.pfp_placeholder)
            .into(holder.image)

        //Click listener for accept button
        holder.acceptButton.setOnClickListener {
            val userHashMap: MutableMap<String, String> = HashMap()

            val userReference = FirebaseDatabase.getInstance().getReference("UserTest")

            //Populate User Has Map and Adds as Friends
            userReference.child(mUser).get().addOnSuccessListener {
                if (it.exists()) {
                    val username = it.child("username").value
                    val imageURL = it.child("imageUrl").value

                    userHashMap["username"] = username as String
                    userHashMap["imageUrl"] = imageURL as String
                    databaseReference.child(list[position].UID).child("Friends").child(mUser)
                        .updateChildren(userHashMap as Map<String, String>)
                }
            }

            //Populate Friend Hash Map and Adds as Friends
            val friendHashMap : MutableMap<String, String> = HashMap()
            friendHashMap["username"] = list[position].username
            friendHashMap["imageUrl"] = list[position].profileImage
            databaseReference.child(mUser).child("Friends").child(list[position].UID).updateChildren(friendHashMap as Map<String, String>)

            //Removes Request
            databaseReference.child(mUser).child("Requests").child(list[position].UID).removeValue()

        }

        //Click listener for reject button
        holder.rejectButton.setOnClickListener {
            databaseReference.child(mUser).child("Requests").child(list[position].UID).removeValue()
        }
    }
}