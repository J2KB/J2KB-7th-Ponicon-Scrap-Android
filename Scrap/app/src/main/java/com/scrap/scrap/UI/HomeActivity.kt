package com.scrap.scrap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.recyclerview.widget.GridLayoutManager
import com.scrap.scrap.CustomAdapter
import com.scrap.scrap.Retrofit.Data.GetAllScrapResult
import com.scrap.scrap.Retrofit.Data.GetScrapResult
import com.scrap.scrap.Retrofit.Data.ScrapResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.TEST.TestData
import com.scrap.scrap.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.CookieManager

class HomeActivity : AppCompatActivity() {

    val binding by lazy { ActivityHomeBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 이거 밖으로 빼자 //
        // 이거 hasExtra로 //
        val id = intent.getLongExtra("id", 0)
        Log.i("MYTAG", "id in home: "+id.toString())

        // 분기 3개
        // 외부에서 스크랩 시, 카테고리 개별 조회, 전체 자료 조회

        var category: Long? = null

        // 카테고리 개별 조회 후 어댑터 바인딩
        if(intent.hasExtra("categoryId")) {
            Log.i("MYTAG","카테고리 개별 조회 중...")
            category = intent.getLongExtra("categoryId", 0)
            Log.i("MYTAG", "categoryId: "+ category.toString())

            RetrofitService.create().getScrap(id, category, "desc").enqueue(object : Callback<GetScrapResult> {
                override fun onResponse(call: Call<GetScrapResult>, response: Response<GetScrapResult>) {
                    // 그리드 뷰 //
                    binding.recylcerView.adapter = CustomAdapter(response.body()!!.result!!.links)
                    binding.recylcerView.layoutManager = GridLayoutManager(this@HomeActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
        }

        // 전체 데이터 조회 후 어댑터 바인딩
        else {
            Log.i("MYTAG","전체 자료 조회 중...")

            RetrofitService.create().getAllScrap(id).enqueue(object : Callback<GetAllScrapResult> {
                override fun onResponse(call: Call<GetAllScrapResult>, response: Response<GetAllScrapResult>) {
                    Log.i("MYTAG",response.toString());
                    Log.i("MYTAG", "body in home : "+response.body()?.result)
                    Log.i("MYTAG", "errorbody : " + response.errorBody().toString())
                    // 그리드 뷰 //
                    binding.recylcerView.adapter = CustomAdapter(response.body()!!.result!!.links)
                    binding.recylcerView.layoutManager = GridLayoutManager(this@HomeActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetAllScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
        }


/*
//        val data:MutableList<TestData> = loadData()

// 테스트용
        adapter.addItem(TestData("https://mania.kr/ms-icon-310x310.png"
            , "2022년 포브스 선정 가장 부유한 미국 스포츠 소유자 1위를 차지한 스티브 발머 - NBA Mania"
            , "https://mania.kr/g2/bbs/board.php?bo_table=news&wr_id=1118499"))
        adapter.addItem(TestData("https://mania.kr/ms-icon-310x310.png"
            , "2022년 포브스 선정 가장 부유한 미국 스포츠 소유자 1위를 차지한 스티브 발머 - NBA Mania"
            , "https://mania.kr/g2/bbs/board.php?bo_table=news&wr_id=1118499"))
        adapter.addItem(TestData("https://mania.kr/ms-icon-310x310.png"
            , "2022년 포브스 선정 가장 부유한 미국 스포츠 소유자 1위를 차지한 스티브 발머 - NBA Mania"
            , "https://mania.kr/g2/bbs/board.php?bo_table=news&wr_id=1118499"))

        binding.button11.setOnClickListener {
            adapter.addItem(TestData("https://mania.kr/ms-icon-310x310.png"
                , "2022년 포브스 선정 가장 부유한 미국 스포츠 소유자 1위를 차지한 스티브 발머 - NBA Mania"
                , "https://mania.kr/g2/bbs/board.php?bo_table=news&wr_id=1118499"))
        }

 */



        var sharedUrl: String? = null
//        var sampleImg: String? = null
//        var title: String? = null

        // 다른 앱에서 공유 시 아이템 추가
        if(intent.hasExtra("sharedUrl")) {
            sharedUrl = intent.getStringExtra("sharedUrl")
            Log.i("MYTAG", sharedUrl!!)
//            if(intent.hasExtra("title")) {
//                title = intent.getStringExtra("title")
//                Log.i("MYTAG", title!!)
//            }
//            if(intent.hasExtra("sampleImg")) {
//                sampleImg = intent.getStringExtra("sampleImg")
//                Log.i("MYTAG", sampleImg!!)
//            }

//            val testData = TestData(sampleImg!!, title!!, sharedUrl)
//            adapter.addItem(testData)
//            adapter.notifyItemInserted(0)

//            val img = R.drawable.test_j2kb
//            var img = sampleImg!!
//            var text1 = title!!
//            var text2 = sharedUrl
//            data.add(TestData(img!!, text1!!, text2!!))


//            adapter.listData = data
//            adapter.notifyItemInserted(0)
//            adapter.notifyDataSetChanged()

        }

        // 아이템 클릭 시 URL 이용 웹 이동





//        // 카테고리 전환 //
//        binding.button5.setOnClickListener {
//            val categoryIntent = Intent(this@HomeActivity, CategoryActivity::class.java)
//            categoryIntent.putExtra("id", id)
//            startActivity(categoryIntent)
//        }
//        // 카테고리 전환 //
//
//        // 마이페이지 전환 //
//        binding.button6.setOnClickListener {
//            val myPageIntent = Intent(this@HomeActivity, MypageActivity::class.java)
//            myPageIntent.putExtra("id", id)
//            startActivity(myPageIntent)
//        }
//        // 마이페이지 전환 //

        val mainIntent = Intent(this@HomeActivity, MainActivity::class.java)
        mainIntent.putExtra("id", id)
        startActivity(mainIntent)
        finish()

    }



    /*
    fun loadData(): MutableList<TestData> {
        val data: MutableList<TestData> = mutableListOf()

//        for(cnt in 1..3) {
//            var img = R.drawable.test_naver
//            val text1 = "인텔리제이 디버깅 해보기"
//            val text2 = "tistory.com"
//
//            var testData = TestData(img, text1, text2)
//            data.add(testData)
//
//            img = R.drawable.test_j2kb
//            testData = TestData(img, text1, text2)
//            data.add(testData)
//
//            img = R.drawable.test_tistory
//            testData = TestData(img, text1, text2)
//            data.add(testData)
//        }

        return data
    }

     */


}