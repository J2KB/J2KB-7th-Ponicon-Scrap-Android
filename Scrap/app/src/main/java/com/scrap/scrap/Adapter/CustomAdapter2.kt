package com.scrap.scrap

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.scrap.scrap.Retrofit.Data.ListCategoryObject
import com.scrap.scrap.UI.HomeActivity
import com.scrap.scrap.UI.MainActivity
import com.scrap.scrap.databinding.ItemGrid2Binding

class CustomAdapter2(data2: ArrayList<ListCategoryObject>?, id: Long?): RecyclerView.Adapter<ViewHolder2>() {
    val data2 = data2
    val id = id
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
    val binding = ItemGrid2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
//        binding.itemName.focusable = View.NOT_FOCUSABLE
        return ViewHolder2(binding, parent.context)
    }

    override fun onBindViewHolder(holder2: ViewHolder2, position: Int) {
        holder2.binding.itemName.text = data2?.get(position)?.name
        holder2.binding.itemCount.text = data2?.get(position)?.numOfLink.toString()

        holder2.binding.root.setOnClickListener{
            // 터치 이벤트
            var intent = Intent(holder2.context, MainActivity::class.java)
            if(position == 0) {
                intent.putExtra("all","all")
            }
            else {
                intent.putExtra("categoryId", data2?.get(position)?.categoryId)
            }
            intent.putExtra("id", id)
            Log.i("MYTAG", "categoryId: "+data2?.get(position)?.categoryId.toString())
            Log.i("MYTAG","pos: "+position.toString())
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(holder2.context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return data2!!.size
    }
}

class ViewHolder2(val binding: ItemGrid2Binding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
}