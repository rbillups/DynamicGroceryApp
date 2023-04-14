package com.example.dynamicgroceryapp.ui.appMain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.ui.appMain.userGroupScreen.Item

class ItemAdapter(val context: FragmentActivity, val itemList: ArrayList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.checklist, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.checkBox.text = currentItem.Item
        holder.checkBox.isChecked = currentItem.checked


        holder.itemView.setOnClickListener {
            // Handle item click event here
        }

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            currentItem.checked = isChecked
            // Handle checkbox state change event here
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
