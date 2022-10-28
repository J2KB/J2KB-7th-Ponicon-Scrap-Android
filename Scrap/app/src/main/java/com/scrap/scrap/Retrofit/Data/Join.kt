package com.scrap.scrap.Retrofit.Data

class Join{
}


data class JoinInfo(
    val email: String?,
    val password: String?,
    val name: String?
)

data class JoinResult(
    val code : Int?,
    val message: String?
)
