package com.scrap.scrap.Retrofit.Data

class Category {
}


data class CategoryInfo(
    val name: String?
)
data class CategoryResult(
    val code : Int?,
    val message: String?,
    val result: CategoryObject?
)
data class CategoryObject(
    val categoryId: Long?
)

data class GetAllCategoryResult(
    val code : Int?,
    val message: String?,
    val result: GetAllCategoryObject?
)
data class GetAllCategoryObject(
    val categories: ArrayList<ListCategoryObject>
)
data class ListCategoryObject(
    val categoryId: Long?,
    val name: String?,
    val numOfLink: Int?,
    val order: Int?
)