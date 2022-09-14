package com.scrap.scrap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scrap.scrap.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageView2.setOnClickListener {
            val loginIntent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}