package com.scrap.scrap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scrap.scrap.Adapter.CustomAdapter4
import com.scrap.scrap.CustomAdapter2
import com.scrap.scrap.MyApplication
import com.scrap.scrap.Pos
import com.scrap.scrap.Retrofit.Data.GetAllCategoryResult
import com.scrap.scrap.Retrofit.Data.Scrap
import com.scrap.scrap.Retrofit.Data.ScrapInfo
import com.scrap.scrap.Retrofit.Data.ScrapResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivityAddScrapBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddScrapActivity : AppCompatActivity() {

    val binding by lazy { ActivityAddScrapBinding.inflate(layoutInflater) }

    //// !!!! ID 값 수정 필요 .  현재 로컬 값임 !!!! ////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//         인텐트를 얻어오고, 액션과 MIME 타입을 가져온다
        val intent: Intent = getIntent()
        val action: String? = intent.action
        val type: String? = intent.type
        val sharedURL: String?
//        var doc: Document? = null
//        var title: String? = null
//        var sampleImg: String? = null

        lateinit var scrapData: ScrapInfo

        val autoCheck: Long = 0
        val id: Long = MyApplication.prefs.getId("id", 0)

        if (id == autoCheck) {
            startActivity(Intent(this@AddScrapActivity, LoginActivity::class.java))
            finish()
        } else {


            //         인텐트 정보가 있는 경우 실행
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    sharedURL = intent.getStringExtra(Intent.EXTRA_TEXT)
                    scrapData = ScrapInfo(sharedURL)
                    Log.i("MYTAG", sharedURL!!)


//                // 코루틴 og:img
//                GlobalScope.launch {
//                    //          이미지 크롤링 with jsoup
//                    doc = Jsoup.connect(sharedURL!!).get()
//                    title = doc!!.select("meta[property=og:title]").attr("content")
//                    sampleImg = doc!!.select("meta[property=og:image]").attr("content")
//                    Log.i("MYTAG", "in couro title : " + title)
//                    Log.i("MYTAG", "in couro img : " + sampleImg)
////                    Log.i("MYTAG", "in couro URL : " + sharedURL)
//                }
                }

                // 전체 카테고리 조회 후 어댑터 바인딩
                RetrofitService.create().getCategory(id)
                    .enqueue(object : Callback<GetAllCategoryResult> {
                        override fun onResponse(
                            call: Call<GetAllCategoryResult>,
                            response: Response<GetAllCategoryResult>
                        ) {
                            // 구분선 넣기
                            val dividerItemDecoration =
                                DividerItemDecoration(
                                    binding.recyclerView.context,
                                    GridLayoutManager(this@AddScrapActivity, 1).orientation
                                )
                            // 그리드 뷰 //
                            Log.i("MYTAG", response.body()!!.result!!.categories.toString())
                            binding.recyclerView.adapter =
                                CustomAdapter4(response.body()!!.result!!.categories, id, scrapData)
                            binding.recyclerView.layoutManager =
                                GridLayoutManager(this@AddScrapActivity, 1)
                            binding.recyclerView.addItemDecoration(dividerItemDecoration)
                            // 그리드 뷰 //
                        }

                        override fun onFailure(call: Call<GetAllCategoryResult>, t: Throwable) {
                            Log.i("MYTAG", t.message.toString())
                            Log.i("MYTAG", "FAIL")
                        }
                    })

                // 저장 버튼
                binding.textView7.setOnClickListener {
                    // 인텐트와 함께 이동
//                val addScrapIntent = Intent(this@AddScrapActivity, HomeActivity::class.java)
//                addScrapIntent.putExtra("sharedUrl", sharedUrl)

//                addScrapIntent.putExtra("title", title)
//                addScrapIntent.putExtra("sampleImg", sampleImg)

                    RetrofitService.create().postScrap(id, Pos.myPos, scrapData)
                        .enqueue(object : Callback<ScrapResult> {
                            override fun onResponse(
                                call: Call<ScrapResult>,
                                response: Response<ScrapResult>
                            ) {
                                Log.i("MYTAG", response.body().toString())
                            }

                            override fun onFailure(call: Call<ScrapResult>, t: Throwable) {
                                Log.i("MYTAG", t.message.toString())
                                Log.i("MYTAG", "FAIL")
                            }
                        })

                    finish()
//            val gotoHomeIntent = Intent(this@AddScrapActivity, HomeActivity::class.java)
//            gotoHomeIntent.putExtra("id", id)
//            startActivity(gotoHomeIntent)
                }

            }
        }
    }
}
