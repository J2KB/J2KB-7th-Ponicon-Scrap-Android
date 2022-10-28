package com.scrap.scrap.Retrofit.Data

class Duplicate{
}


data class DuplicateResult(
    val code : Int?,
    val message: String?,
    val result: DubplicateObject?
)

data class DubplicateObject(
    val isDuplicate: Boolean?
)

