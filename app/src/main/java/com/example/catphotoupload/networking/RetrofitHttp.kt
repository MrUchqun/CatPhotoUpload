package com.example.catphotoupload.networking

import com.example.catphotoupload.networking.service.CatPhotoService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitHttp {
    private const val IS_TESTER = true
    private const val SERVER_DEVELOPMENT = "https://api.thecatapi.com/v1/"
    private const val SERVER_PRODUCTION = "https://api.thecatapi.com/v1/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER)
            return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val photoService: CatPhotoService = retrofit.create(CatPhotoService::class.java)

    fun getMultipartBody(key: String, file: File): MultipartBody.Part {
        val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/jpg"), file)
        return MultipartBody.Part.createFormData(key, file.name, reqFile)
    }


}