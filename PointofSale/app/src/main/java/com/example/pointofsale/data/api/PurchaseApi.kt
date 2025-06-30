package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PurchaseApi {

    @GET("purchases")
    fun getPurchases(): Call<ResponsePurchase>

    @GET("purchases/{id}")
    fun getPurchaseById(@Path("id") id: String): Call<ResponseStorePurchase>

    @POST("purchases")
    fun storePurchase(@Body body: RequestBody): Call<ResponseStorePurchase>

    @GET("purchases-detail/by-purchase/{purchase_id}")
    fun getPurchaseDetails(@Path("purchase_id") purchaseId: String): Call<ResponsePurchaseDetail>
}
