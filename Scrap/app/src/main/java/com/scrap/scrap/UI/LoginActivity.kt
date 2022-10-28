package com.scrap.scrap.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.scrap.scrap.MyApplication
import com.scrap.scrap.R
import com.scrap.scrap.Retrofit.Data.LoginInfo
import com.scrap.scrap.Retrofit.Data.LoginResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivityLoginBinding
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//// !!!! 체크박스 수정 !!!! ////

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    lateinit var loginData: LoginInfo
    var username: String? = null
    var password: String? = null
    var autoLogin: Boolean? = null
    var autoCheck: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.i("MYTAG", "autoLogin ID : "+MyApplication.prefs.getId("id", 0).toString())
//        if (MyApplication.prefs.getId("id", 0) != autoCheck) {
//            val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
//            loginIntent.putExtra("id", MyApplication.prefs.getId("id", 0))
////            startActivity(loginIntent)
//        }

        Log.i("MYTAG", "auto username : "+MyApplication.prefs.getString("username", "null"))
        Log.i("MYTAG", "auto password : "+MyApplication.prefs.getString("password", "null"))


        if(MyApplication.prefs.getString("username", "null") != "null" ) {
            RetrofitService.create().postLogin(
                LoginInfo(
                    MyApplication.prefs.getString("username", "null"),
                    MyApplication.prefs.getString("password", "null"),
                    true)
            ).enqueue(object : Callback<LoginResult> {
                override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                    Log.i("MYTAG", "body: "+response.body().toString())
                    val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    loginIntent.putExtra("id", response.body()!!.result!!.id)
                    startActivity(loginIntent)
                    finish()
                }
                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.i("MYTAG",t.message.toString())
                    Log.i("MYTAG","FAIL")
                }
            })
        }


        binding.checkBox.setOnClickListener{
            if (binding.checkBox.isChecked) {
                binding.checkBox.setButtonDrawable(R.drawable.check_box_full)
            } else {
                binding.checkBox.setButtonDrawable(R.drawable.check_box_outline_blank)
            }
        }

        // 기본 로그인
        binding.buttonLogin.setOnClickListener {
            loginFun()
        }

            /*
        // 카카오톡 로그인
        binding.button2.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("MYTAG", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.d("MYTAG", "로그인 성공 ${token.accessToken}")
                }
            }
        }

              */

            // 회원가입
            binding.textView2.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }

        }

    override fun onBackPressed() {
        finish()
    }

    fun loginBinding() {
        username = binding.loginId.text.toString()
        password = binding.loginPassword.text.toString()
        autoLogin = binding.checkBox.isChecked

        loginData = LoginInfo(username, password, autoLogin)

        Log.i("MYTAG","loginData : "+loginData.toString())
    }
    fun loginFun() {
        loginBinding()

        RetrofitService.create().postLogin(loginData).enqueue(object : Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.i("MYTAG", "body: "+response.body().toString())

                val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
//                val loginIntent = Intent(this@LoginActivity, HomeActivity::class.java)

                loginIntent.putExtra("id", response.body()!!.result!!.id)

                MyApplication.prefs.setId("id", response.body()!!.result!!.id!!)
                Log.i("MYTAG", MyApplication.prefs.getId("id",0).toString())

                if(autoLogin == true) {
                    Log.i("MYTAG", "NOW AUTOLOGIN")
                    MyApplication.prefs.setString("username", username)
                    MyApplication.prefs.setString("password", password)

                }

//                Log.i("MYTAG", "id: "+response.body()!!.result!!.id.toString())
                startActivity(loginIntent)
                finish()
                // 예외처리 나중에.. 지금 아디 다르게 치면 response 그냥 null로 옴
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })

    }
    }
