package com.fajarkhan.nasapicapp.repository

import com.fajarkhan.nasapicapp.model.PictureResponseModel

interface PictureRepository {
    suspend fun getPictureResponse(): List<PictureResponseModel>?
}