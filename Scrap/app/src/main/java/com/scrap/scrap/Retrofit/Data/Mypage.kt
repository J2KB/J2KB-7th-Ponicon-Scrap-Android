package com.scrap.scrap.Retrofit.Data

class Mypage {
}


data class MypageResult(
    val code : Int?,
    val message: String?,
    val result: MypageObject?
)
data class MypageObject(
    val name: String?,
    val username: String?
)