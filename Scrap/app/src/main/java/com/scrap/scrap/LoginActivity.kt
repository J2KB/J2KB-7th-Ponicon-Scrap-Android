package com.scrap.scrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.scrap.scrap.databinding.ActivityLoginBinding
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {

    val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    }
}