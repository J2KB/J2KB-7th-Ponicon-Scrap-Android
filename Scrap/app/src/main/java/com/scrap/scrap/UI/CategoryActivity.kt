package com.scrap.scrap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.scrap.scrap.CustomAdapter2
import com.scrap.scrap.MultiviewAdpater
import com.scrap.scrap.Retrofit.Data.GetAllCategoryResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.TEST.TestData2
import com.scrap.scrap.databinding.ActivityCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {

    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getLongExtra("id", 0)
        Log.i("MYTAG", "id: "+id.toString())

/*
//        val data2:MutableList<TestData2> = loadData2()
//        val data2: MutableList<TestData2> = mutableListOf()
//        data2.add(TestData2("모든 자료", "57", 1))
//        data2.add(TestData2("분류되지 않은 자료", "57", 1))

//        var cnt: Int = 2
//
//        val adapter2 = MultiviewAdpater(this)
//        adapter2.datas = data2

 */

        // 전체 카테고리 조회 후 어댑터 바인딩
        RetrofitService.create().getCategory(id).enqueue(object : Callback<GetAllCategoryResult>{
            override fun onResponse(call: Call<GetAllCategoryResult>, response: Response<GetAllCategoryResult>) {
                // 그리드 뷰 //
                Log.i("MYTAG", response.body()!!.result!!.categories.toString())
                binding.recyclerView.adapter = CustomAdapter2(response.body()!!.result!!.categories, id)
                binding.recyclerView.layoutManager = GridLayoutManager(this@CategoryActivity, 1)
                // 그리드 뷰 //
            }

            override fun onFailure(call: Call<GetAllCategoryResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })

        binding.button7.setOnClickListener {
            finish()
        }

        binding.buttonAdd.setOnClickListener {

            val gotoAddIntent = Intent(this@CategoryActivity, CategoryAddActivity::class.java)
            gotoAddIntent.putExtra("id", id)
            startActivity(gotoAddIntent)

            /*
            // test add
            data2.add(TestData2("add...", "56"))
            adapter2.listData = data2

            binding.recyclerView.adapter = adapter2
            binding.recyclerView.layoutManager = GridLayoutManager(this, 1)

             */
//            adapter2.datas.add(TestData2("","", 2))
//            adapter2.notifyItemInserted(cnt++)


        }
    }
/*
    fun loadData2(): MutableList<TestData2> {
        val data2: MutableList<TestData2> = mutableListOf()

//        for(cnt in 1..3) {
//            val text1 = "test"
//            val text2 = "56"
//            val type1 = 1
//            val type2 = 2
//
//            var testData2 = TestData2(text1, text2,type1)
//            data2.add(testData2)
//
////            testData2 = TestData2(text1, text2,type2)
////            data2.add(testData2)
//        }

        val text1 = "모든 자료"
        val num1 = "57"
        val text2 = "분류되지 않은 자료"
        val num2 = "13"
        val type1 = 1   // 일반 카테고리
        val type2 = 2   // 카테고리 생성 시 UI

        return data2
    }

 */
}