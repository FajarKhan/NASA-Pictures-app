package com.fajarkhan.nasapicapp.repository

import android.content.Context
import com.fajarkhan.nasapicapp.model.PictureResponseModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class PictureRepositoryImpl(private val context: Context) : PictureRepository {


    override suspend fun getPictureResponse(): List<PictureResponseModel>? {
        /**
         * Created a new listype because moshi.apapter dosen't take directly the List<PictureRespons> as a parameter
         * */
        val listType =
            Types.newParameterizedType(List::class.java, PictureResponseModel::class.java)
        val adapter: JsonAdapter<List<PictureResponseModel>> = getMoshiInstance().adapter(listType)

        val nasaJson = context.assets.open("data.json").bufferedReader().use { it.readText() }
        return adapter.fromJson(nasaJson)
    }


    /**
     * Creating a static object to get the instance of the moshi builder
     * */
    companion object MoshiFactory {
        private val moshi: Moshi? = null

        // Singleton moshi initialization
        fun getMoshiInstance(): Moshi {
            return moshi ?: Moshi.Builder().build()
        }
    }
}