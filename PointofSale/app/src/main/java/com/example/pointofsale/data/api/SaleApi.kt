package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SaleApi {

    @GET("sales")
    fun getSales(): Call<ResponseSale>

    @GET("sales/{id}")
    fun getSaleById(@Path("id") id: String): Call<ResponseStoreSale>

    @POST("sales")
    fun storeSale(@Body params: RequestBody): Call<ResponseStoreSale>

    @GET("sales-detail/{sale_id}")
    fun getSaleDetails(@Path("sale_id") transactionId: String): Call<ResponseSaleDetail>
}