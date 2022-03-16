package com.example.catphotoupload.networking.service

import com.example.catphotoupload.model.Breed
import com.example.catphotoupload.model.Image
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CatPhotoService {

    companion object {
        private const val API_KEY = "50b5dedd-24cd-4cf5-bc22-195a98b0312d"
        private const val X_API_KEY = "x-api-key"
    }

    @Headers("$X_API_KEY:$API_KEY")
    @GET("breeds")
    fun getBreeds(@Query("limit") limit: Int, @Query("page") page: Int): Call<ArrayList<Breed>>

    @Headers("$X_API_KEY:$API_KEY")
    @GET("images")
    fun getMyCats(): Call<ArrayList<Image>>

    @Headers("$X_API_KEY:$API_KEY")
    @Multipart
    @POST("images/upload")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("sub_id") subId: String?
    ): Call<ResponseBody>


}