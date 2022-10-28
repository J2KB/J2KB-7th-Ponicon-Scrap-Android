package com.scrap.scrap


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scrap.scrap.TEST.TestData2

class MultiviewAdpater(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<TestData2>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val view : View?
        return when(viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_grid_2,
                    parent,
                    false
                )
                MultiViewHolder1(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_grid_add,
                    parent,
                    false
                )
                MultiViewHolder2(view)
            }
        }
    }
    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int {
        return datas[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            1 -> {
                (holder as MultiViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as MultiViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    inner class MultiViewHolder1(view: View) : RecyclerView.ViewHolder(view) {

        private val txt1: TextView = view.findViewById(R.id.itemName)
//        private val txt2: TextView = view.findViewById(R.id.itemNumOfLink)


        fun bind(item: TestData2) {
            txt1.text = item.text1
//            txt2.text = item.text2
        }
    }
    inner class MultiViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        private val txt1: TextView = view.findViewById(R.id.addItem)


        fun bind(item: TestData2) {
            txt1.text = item.text1
        }
    }
}