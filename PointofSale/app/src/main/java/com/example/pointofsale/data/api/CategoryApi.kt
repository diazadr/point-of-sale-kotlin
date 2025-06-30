package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CategoryApi {

    @GET("categories")
    fun getCategory(): Call<ResponseCategory>

    @POST("categories")
    fun storeCategory(@Body params: RequestBody): Call<ResponseStoreCategory>

    @PUT("categories/{id}")
    fun updateCategory(@Path("id") id: String, @Body params: RequestBody): Call<ResponseUpdateCategory>

    @DELETE("categories/{id}")
    fun deleteCategory(@Path("id") id: String): Call<ResponseDeleteCategory>
}
