package com.scrap.scrap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.scrap.scrap.Retrofit.Data.JoinInfo
import com.scrap.scrap.Retrofit.Data.JoinResult
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater)}

    val retro = RetrofitService.create()

    lateinit var joinData: JoinInfo
    var email: String? = null
    var password: String? = null
    var name: String? = null

    val namePattern = "^[a-zA-Z가-힣]*$"
    val pattern1 = Pattern.compile(namePattern)
    val emailPattern = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
    val pattern2 = Pattern.compile(emailPattern)
    val passwordPattern = "^[a-zA-Z0-9]{5,16}$"
    val pattern3 = Pattern.compile(passwordPattern)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        if(binding.joinPassword.text == "aaa") {
//        }

        // 패턴 사용
        val watcherName = NameWatcher()
        val watcherId = IdWatcher()
        val watcherPw = PwWatcher()
        val watcherPwChk = PwChkWatcher()
        binding.joinName.addTextChangedListener(watcherName)
        binding.joinId.addTextChangedListener(watcherId)
        binding.joinPassword.addTextChangedListener(watcherPw)
        binding.joinPasswordAgain.addTextChangedListener(watcherPwChk)


        binding.imageView2.setOnClickListener {
//            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }

        binding.buttonJoin.setOnClickListener {
            joinFun()
            //// 분기 만들어야 함 ////
        }
    }

    inner class NameWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "before : $p0 $p1 $p2 $p3")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "on : $p0 $p1 $p2 $p3")
            if (p3 == 0) {
                // 로직 점검 및 숫자 1개 입력 시 text 바뀜
                binding.textView5.text = "*이름을 입력하세요"
                binding.textView5.visibility = View.VISIBLE
            }
            else {
                binding.textView5.visibility = View.INVISIBLE

                if (pattern1.matcher(p0).find()) {
//                    binding.textView5.visibility = View.INVISIBLE
                }
                else {
                    binding.textView5.text = "*한글 또는 영어로만 이뤄질 수 있습니다"
                    binding.textView5.visibility = View.VISIBLE
                }
            }

        }

        override fun afterTextChanged(p0: Editable?) {
            Log.i("MYTAG", "after : $p0")
        }
    }
    inner class IdWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "before : $p0 $p1 $p2 $p3")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "on : $p0 $p1 $p2 $p3")
            if (p3 == 0) {
                binding.textView4.text = "*이메일을 입력하세요"
                binding.textView4.visibility = View.VISIBLE
            }
            else {
                binding.textView4.visibility = View.INVISIBLE

                if (pattern2.matcher(p0).find()) {
                }
                else {
                    binding.textView4.text = "*이메일 형식으로 입력해주세요"
                    binding.textView4.visibility = View.VISIBLE
                }
            }
        }
        override fun afterTextChanged(p0: Editable?) {
            Log.i("MYTAG", "after : $p0")
        }
    }

    inner class PwWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "before : $p0 $p1 $p2 $p3")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "on : $p0 $p1 $p2 $p3")
            if (p3 == 0) {
                binding.textView8.text = "*비밀번호를 입력하세요"
                binding.textView8.visibility = View.VISIBLE
            }
            else {
                binding.textView8.visibility = View.INVISIBLE

                if (pattern3.matcher(p0).find()) {
                }
                else {
                    binding.textView8.text = "*5~16자의 영어, 숫자를 포함해야 합니다"
                    binding.textView8.visibility = View.VISIBLE
                }
            }
        }
        override fun afterTextChanged(p0: Editable?) {
            Log.i("MYTAG", "after : $p0")
        }
    }

    inner class PwChkWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "before : $p0 $p1 $p2 $p3")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("MYTAG", "on : $p0 $p1 $p2 $p3")
            Log.i("MYTAG", "p0 : $p0")
            Log.i("MYTAG", "pw : ${binding.joinPassword.text}")
            if (p3 == 0) {
                binding.textView9.text = "*비밀번호 확인을 입력하세요"
                binding.textView9.visibility = View.VISIBLE
            }
            else {
                binding.textView9.visibility = View.INVISIBLE

                if (p0.toString() == binding.joinPassword.text.toString()) {
                    binding.textView9.visibility = View.INVISIBLE
                }
                else {
                    binding.textView9.text = "*비밀번호와 일치하지 않습니다"
                    binding.textView9.visibility = View.VISIBLE
                }
            }
        }
        override fun afterTextChanged(p0: Editable?) {
            Log.i("MYTAG", "after : $p0")
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun joinBinding() {
        email = binding.joinId.text.toString()
        password = binding.joinPassword.text.toString()
        name = binding.joinName.text.toString()

        joinData = JoinInfo(email,password, name)

        Log.i("MYTAG","joinData : "+joinData.toString())
    }
    fun joinFun() {
        joinBinding()

        retro.postJoin(joinData).enqueue(object : Callback<JoinResult> {
            override fun onResponse(call: Call<JoinResult>, response: Response<JoinResult>) {
                Log.i("MYTAG", response.body().toString())

//                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            }

            override fun onFailure(call: Call<JoinResult>, t: Throwable) {
                Log.i("MYTAG",t.message.toString())
                Log.i("MYTAG","FAIL")
            }
        })
    }
}