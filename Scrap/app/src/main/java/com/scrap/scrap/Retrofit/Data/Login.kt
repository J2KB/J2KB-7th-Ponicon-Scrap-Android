package com.scrap.scrap.Retrofit.Data

class Login {
}


data class LoginInfo (
    val username: String?,
    val password: String?,
    val autoLogin: Boolean?
)

data class LoginResult (
    val code : Int?,
    val message: String?,
    val result: LoginObject?
)
data class LoginObject(
    val id: Long?
)
