package com.fajarkhan.nasapicapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PictureResponseModel(
    val title: String,
    val date: String,
    val explanation: String,
    //Copyright data can be null sometimes
    val copyright: String? = null,
    @Json(name = "url") val imageUrl: String
)

