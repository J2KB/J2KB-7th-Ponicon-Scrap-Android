package com.scrap.scrap.Adapter


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.scrap.scrap.Pos
import com.scrap.scrap.Retrofit.Data.ListCategoryObject
import com.scrap.scrap.Retrofit.Data.ScrapInfo
import com.scrap.scrap.Retrofit.Data.ScrapResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.UI.AddScrapActivity
import com.scrap.scrap.UI.HomeActivity
import com.scrap.scrap.databinding.ActivityAddScrapBinding
import com.scrap.scrap.databinding.ItemGrid2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomAdapter4(data4: ArrayList<ListCategoryObject>?, id: Long?, scrapData: ScrapInfo): RecyclerView.Adapter<ViewHolder4>() {
    val data4 = data4
    val id = id
    val scrapData = scrapData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder4 {
        val binding = ItemGrid2Binding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder4(binding, parent.context)
    }

    override fun onBindViewHolder(holder4: ViewHolder4, position: Int) {
        holder4.binding.itemName.text = data4?.get(position)?.name
        holder4.binding.itemCount.text = data4?.get(position)?.numOfLink.toString()

        holder4.binding.root.setOnClickListener{
            // 터치 이벤트
            if(Pos.myRoot != null) {
                Pos.myRoot!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            holder4.binding.root.setBackgroundColor(Color.parseColor("#D7ECF1"))
            Pos.myPos = data4?.get(position)?.categoryId
            Log.i("MYTAG", "categoryId : "+Pos.myPos.toString())

            Pos.myRoot = holder4.binding.root
        }

/*
        holder4.binding2.textView7.setOnClickListener {
            Log.i("MYTAG", "categoryId : "+myPos.toString())

            RetrofitService.create().postScrap(id,myPos, scrapData)
                .enqueue(object : Callback<ScrapResult> {
                    override fun onResponse(call: Call<ScrapResult>, response: Response<ScrapResult>) {
                        Log.i("MYTAG", response.body().toString())

                    }

                    override fun onFailure(call: Call<ScrapResult>, t: Throwable) {
                        Log.i("MYTAG",t.message.toString())
                        Log.i("MYTAG","FAIL")
                    }
                })

            System.exit(0)

            var intent = Intent(holder4.context, HomeActivity::class.java)
            intent.putExtra("categoryId", data4?.get(position)?.categoryId)
            intent.putExtra("id", id)
            Log.i("MYTAG", "categoryId: "+data4?.get(position)?.categoryId.toString())
            startActivity(holder4.context,intent,null)


        }

 */
    }

    override fun getItemCount(): Int {
        return data4!!.size
    }
}

class ViewHolder4(val binding: ItemGrid2Binding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
}