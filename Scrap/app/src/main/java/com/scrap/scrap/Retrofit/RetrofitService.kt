package com.scrap.scrap.Retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scrap.scrap.Retrofit.Data.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.CookieManager

interface RetrofitService {

    @POST("/user/join")
    fun postJoin(
        @Body jsonparams: JoinInfo
    ): Call<JoinResult>

    @GET("/user/duplicate")
    fun getDuplicateId(
        @Query("id") id: String?
    ): Call<DuplicateResult>

    @POST("/user/login")
    fun postLogin(
        @Body jsonparams: LoginInfo
    ): Call<LoginResult>

//    // 카카오 로그인 later
//    @GET("/user/login/kakao")

    @GET("/user/logout")
    fun getLogout(
      // nothing...
    ): Call<LogoutResult>

    @GET("/auth/user/mypage")
    fun getMypage(
        @Query("id") id: Long?
    ): Call<MypageResult>

    @POST("/data")
    fun postScrap(
        @Query("id") id: Long?,
        @Query("category") category: Long?,
        @Body jsonparams: ScrapInfo
    ): Call<ScrapResult>

    @GET("/auth/data")
    fun getScrap(
        @Query("id") id: Long?,
        @Query("category") category: Long?,
        @Query("seq") seq: String?
    ): Call<GetScrapResult>

    @GET("/auth/data/all")
    fun getAllScrap(
        @Query("id") id: Long?
    ): Call<GetAllScrapResult>

    @POST("/auth/category")
    fun postCategory(
        @Query("id") id: Long?,
        @Body jsonparams: CategoryInfo
    ): Call<CategoryResult>

//    @GET("/auth/category/all")
    @GET("/category/all")
    fun getCategory(
        @Query("id") id: Long?
    ): Call<GetAllCategoryResult>

    @DELETE("/auth/data/{user_id}")
    fun deleteScrap(
        @Path("user_id") user_id: Long?,
        @Query("link_id") link_id: Long?
    ): Call<DeleteScrapResult>


    //-----------------------------------------------------------------------------


    // 싱글톤
    companion object {
        //        private const val BASE_URL = "https://test-domains.shop"
        private const val BASE_URL = "https://scrap.hana-umc.shop"

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager())) //쿠키매니저 연결
            .build()

        fun create(): RetrofitService {

            val gson : Gson =   GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(ScalarsConverterFactory.create())    // List가 아닌 response 사용 시

                .build()
                .create(RetrofitService::class.java)

        }
    }
}