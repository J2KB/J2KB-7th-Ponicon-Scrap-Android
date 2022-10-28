package com.scrap.scrap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.scrap.scrap.MyApplication
import com.scrap.scrap.R
import com.scrap.scrap.Retrofit.Data.LogoutResult
import com.scrap.scrap.Retrofit.Data.MypageResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivityMypageBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageActivity : AppCompatActivity() {

    val binding by lazy {ActivityMypageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getLongExtra("id", 0)
        Log.i("MYTAG", "id: "+id.toString())

        mypageFun(id)

        binding.buttonLogout.setOnClickListener {
            logoutFun()
        }

        binding.imageView7.setOnClickListener {
            finish()
        }
    }

    fun mypageBinding(name: String?, username: String?) {
        binding.mypageName.text = name
        binding.mypageUsername.text = username
    }
    fun mypageFun(id: Long?) {
        RetrofitService.create().getMypage(id).enqueue(object: Callback<MypageResult>{
            override fun onResponse(call: Call<MypageResult>, response: Response<MypageResult>) {
                mypageBinding(response.body()!!.result!!.name, response.body()!!.result!!.username)
            }

            override fun onFailure(call: Call<MypageResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })
    }

    fun logoutFun() {
        RetrofitService.create().getLogout().enqueue(object: Callback<LogoutResult>{
            override fun onResponse(call: Call<LogoutResult>, response: Response<LogoutResult>) {
                Log.i("MYTAG", response.body().toString())
                Log.i("MYTAG", response.code().toString())
                // !!! 토스트 메세지 출력 필요

                MyApplication.prefs.setId("id", 0)
                MyApplication.prefs.setString("username","null")
                MyApplication.prefs.setString("password", "null")

                finishAffinity()
                startActivity(Intent(this@MypageActivity, LoginActivity::class.java))
            }

            override fun onFailure(call: Call<LogoutResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")                }
        })
    }
}