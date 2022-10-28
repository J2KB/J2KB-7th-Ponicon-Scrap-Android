package com.scrap.scrap.Retrofit.Data

class Scrap {
}


//---------------------------
// post scrap
data class ScrapInfo(
//    val link: String?,
//    val title: String?,
//    val imgUrl: String?,
//    val category: Long?,
    val baseURL: String?
)

data class ScrapResult(
    val code : Int?,
    val message: String?,
    val result: ScrapObject?
)
data class ScrapObject(
    val linkId: Long?
)
//---------------------------

//---------------------------
// get scrap
data class GetScrapResult(
    val code : Int?,
    val message: String?,
    val result: GetScrapObject?
)
data class GetScrapObject(
    val totalLink: Int?,
    val links: ArrayList<ListScrapObject>?
)
//---------------------------

//---------------------------
// get all scrap
data class GetAllScrapResult(
    val code : Int?,
    val message: String?,
    val result: GetAllScrapObject?
)
data class GetAllScrapObject(
    val links: ArrayList<ListScrapObject>?
)
//---------------------------

// delete scrap
data class DeleteScrapResult(
    val code: Int?,
    val message: String?
)

// 공통 List 객체
data class ListScrapObject(
    val linkId: Long?,
    val link: String?,
    val title: String?,
    val domain: String?,
    val imgUrl: String?
)