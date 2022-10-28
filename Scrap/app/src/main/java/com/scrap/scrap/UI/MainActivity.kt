package com.scrap.scrap.UI
import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat

import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.scrap.scrap.CustomAdapter
import com.scrap.scrap.CustomAdapter2
import com.scrap.scrap.MyApplication
import com.scrap.scrap.R
import com.scrap.scrap.Retrofit.Data.*
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivityMainBinding
import com.scrap.scrap.databinding.ItemGrid2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val bindingItemGrid by lazy { ItemGrid2Binding.inflate(layoutInflater) }

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    val id = MyApplication.prefs.getId("id",0)

    var data:  ArrayList<ListCategoryObject>? = null

//    override fun onResume() {
//        super.onResume()
//        Log.i("MYTAG","resume")
//    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if(intent?.getStringExtra("del") == "del") {
            Log.i("MYTAG", "newIntent")

            Log.i("MYTAG", "전체 자료 조회 중...")

            RetrofitService.create().getAllScrap(id).enqueue(object : Callback<GetAllScrapResult> {
                override fun onResponse(
                    call: Call<GetAllScrapResult>,
                    response: Response<GetAllScrapResult>
                ) {
                    Log.i("MYTAG", response.toString());
                    Log.i("MYTAG", "body in main : " + response.body()?.result)
//                Log.i("MYTAG", "errorbody : " + response.errorBody().toString())
                    // 그리드 뷰 //
                    binding.IncludeHome.recylcerView.adapter =
                        CustomAdapter(response.body()!!.result!!.links)
                    binding.IncludeHome.recylcerView.layoutManager =
                        GridLayoutManager(this@MainActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetAllScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val id = intent.getLongExtra("id", 0)
//        var id = MyApplication.prefs.getId("id",0)
        Log.i("MYTAG", "id in main: "+id.toString())
        var category: Long? = null

//        val shared = getSharedPreferences("auto", Context.MODE_PRIVATE)
//        Log.i("MYTAG",shared.getLong("id", 0).toString())


        binding.IncludeHome.IncludeAppbar.button6.setOnClickListener {
            val myPageIntent = Intent(this@MainActivity, MypageActivity::class.java)
            myPageIntent.putExtra("id", id)
            startActivity(myPageIntent)
        }


        if(intent.hasExtra("all")) {
            Log.i("MYTAG","전체 카테고리 자료 조회 중...")
            RetrofitService.create().getAllScrap(id).enqueue(object : Callback<GetAllScrapResult> {
                override fun onResponse(
                    call: Call<GetAllScrapResult>,
                    response: Response<GetAllScrapResult>
                ) {
                    // 그리드 뷰 //
                    binding.IncludeHome.recylcerView.adapter =
                        CustomAdapter(response.body()!!.result!!.links)
                    binding.IncludeHome.recylcerView.layoutManager =
                        GridLayoutManager(this@MainActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetAllScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
        }

        else if(intent.hasExtra("categoryId")) {
            Log.i("MYTAG","개별 카테고리 자료 조회 중...")
            category = intent.getLongExtra("categoryId", 0)
            Log.i("MYTAG", "categoryId: "+ category.toString())

            RetrofitService.create().getScrap(id, category, "desc").enqueue(object : Callback<GetScrapResult> {
                override fun onResponse(call: Call<GetScrapResult>, response: Response<GetScrapResult>) {
                    // 그리드 뷰 //
                    binding.IncludeHome.recylcerView.adapter = CustomAdapter(response.body()!!.result!!.links?.reversed() as ArrayList<ListScrapObject> /* = java.util.ArrayList<com.scrap.scrap.Retrofit.Data.ListScrapObject> */)
                    binding.IncludeHome.recylcerView.layoutManager = GridLayoutManager(this@MainActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
        }

        else {
            /***********************************************************************/
            Log.i("MYTAG", "전체 자료 조회 중...")

            RetrofitService.create().getAllScrap(id).enqueue(object : Callback<GetAllScrapResult> {
                override fun onResponse(
                    call: Call<GetAllScrapResult>,
                    response: Response<GetAllScrapResult>
                ) {
                    Log.i("MYTAG", response.toString());
                    Log.i("MYTAG", "body in main : " + response.body()?.result)
//                    Log.i("MYTAG", "errorbody : " + response.errorBody().toString())
                    // 그리드 뷰 //
                    binding.IncludeHome.recylcerView.adapter =
                        CustomAdapter(response.body()!!.result!!.links)
                    binding.IncludeHome.recylcerView.layoutManager =
                        GridLayoutManager(this@MainActivity, 1)
                    // 그리드 뷰 //
                }

                override fun onFailure(call: Call<GetAllScrapResult>, t: Throwable) {
                    Log.i("MYTAG", t.message.toString())
                    Log.i("MYTAG", "FAIL")
                }
            })
            /***********************************************************************/
        }

        // 전체 카테고리 조회 후 어댑터 바인딩
        RetrofitService.create().getCategory(id).enqueue(object : Callback<GetAllCategoryResult> {
            override fun onResponse(call: Call<GetAllCategoryResult>, response: Response<GetAllCategoryResult>) {
                // 그리드 뷰 //
                Log.i("MYTAG", response.body()!!.result!!.categories.toString())
                data = response.body()!!.result!!.categories
                binding.RecyclerView.adapter = CustomAdapter2(data, id)
                binding.RecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 1)
                // 그리드 뷰 //


            }

            override fun onFailure(call: Call<GetAllCategoryResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })

//        binding.IncludeHeader.buttonAdd.setOnClickListener {
//            data?.add(ListCategoryObject(0,"",0,0))
//            bindingItemGrid.itemName.focusable = View.FOCUSABLE
//            //            bindingItemGrid.itemName.inputType = InputType.TYPE_CLASS_TEXT
//            binding.RecyclerView.adapter?.notifyDataSetChanged()
//        }


        val toolbar: Toolbar = findViewById(R.id.toolbar) // toolBar를 통해 App Bar 생성
        setSupportActionBar(toolbar) // 툴바 적용

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }

    // 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when(item!!.itemId){
            android.R.id.home->{
                // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_item1-> Toast.makeText(this,"menu_item1 실행",Toast.LENGTH_SHORT).show()
//            R.id.menu_item2-> Toast.makeText(this,"menu_item2 실행",Toast.LENGTH_SHORT).show()
//            R.id.menu_item3-> Toast.makeText(this,"menu_item3 실행",Toast.LENGTH_SHORT).show()
//        }
        return false
    }
}