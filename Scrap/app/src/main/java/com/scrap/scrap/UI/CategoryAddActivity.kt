package com.scrap.scrap.UI

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.scrap.scrap.Adapter.CustomAdapter3
import com.scrap.scrap.CustomAdapter2
import com.scrap.scrap.R
import com.scrap.scrap.Retrofit.Data.CategoryInfo
import com.scrap.scrap.Retrofit.Data.CategoryResult
import com.scrap.scrap.Retrofit.Data.GetAllCategoryResult
import com.scrap.scrap.Retrofit.Data.LoginInfo
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivityCategoryAddBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryAddActivity : AppCompatActivity() {

    val binding by lazy {ActivityCategoryAddBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getLongExtra("id", 0)
        Log.i("MYTAG", "id: "+id.toString())

        RetrofitService.create().getCategory(id).enqueue(object : Callback<GetAllCategoryResult> {
            override fun onResponse(call: Call<GetAllCategoryResult>, response: Response<GetAllCategoryResult>) {
                // 그리드 뷰 //
                Log.i("MYTAG", response.body()!!.result!!.categories.toString())
                binding.recyclerView.adapter = CustomAdapter3(response.body()!!.result!!.categories, id)
                binding.recyclerView.layoutManager = GridLayoutManager(this@CategoryAddActivity, 1)
                // 그리드 뷰 //
            }

            override fun onFailure(call: Call<GetAllCategoryResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })

        binding.button3.setOnClickListener {
            val name: String? = binding.addText.text.toString()
            val categoryData = CategoryInfo(name)

            RetrofitService.create().postCategory(id, categoryData).enqueue(object : Callback<CategoryResult>{
                override fun onResponse(call: Call<CategoryResult>, response: Response<CategoryResult>) {
                    Log.i("MYTAG", response.body().toString())

                    val gotoCategoryIntent = Intent(this@CategoryAddActivity, CategoryActivity::class.java)
                    gotoCategoryIntent.putExtra("id", id)
                    gotoCategoryIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(gotoCategoryIntent)
                    //// finish 이후
                }

                override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                    Log.i("MYTAG",t.message.toString())
                    Log.i("MYTAG","FAIL")
                }
            })
        }
    }
}