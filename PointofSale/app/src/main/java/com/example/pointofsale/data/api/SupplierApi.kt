package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SupplierApi {

    @GET("suppliers")
    fun getSuppliers(): Call<ResponseSupplier>

    @POST("suppliers")
    fun storeSupplier(@Body params: RequestBody): Call<ResponseStoreSupplier>

    @PUT("suppliers/{id}")
    fun updateSupplier(@Path("id") id: String, @Body params: RequestBody): Call<ResponseUpdateSupplier>

    @DELETE("suppliers/{id}")
    fun deleteSupplier(@Path("id") id: String): Call<ResponseDeleteSupplier>
}