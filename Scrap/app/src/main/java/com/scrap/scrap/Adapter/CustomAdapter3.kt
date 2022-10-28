package com.scrap.scrap.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.scrap.scrap.Retrofit.Data.ListCategoryObject
import com.scrap.scrap.UI.CategoryAddActivity
import com.scrap.scrap.UI.HomeActivity
import com.scrap.scrap.databinding.ActivityMypageBinding.inflate
import com.scrap.scrap.databinding.ItemGrid2Binding
import com.scrap.scrap.databinding.ItemGridAddBinding

class CustomAdapter3(data3: ArrayList<ListCategoryObject>?, id: Long?): RecyclerView.Adapter<ViewHolder3>() {
    val data3 = data3
    val id = id
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder3 {
        val binding = ItemGridAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder3(binding, parent.context)
    }

    override fun onBindViewHolder(holder3: ViewHolder3, position: Int) {
        holder3.binding.addItem.setText(data3?.get(position)?.name)
    }

    override fun getItemCount(): Int {
        return data3!!.size
    }

}

class ViewHolder3(val binding: ItemGridAddBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
}