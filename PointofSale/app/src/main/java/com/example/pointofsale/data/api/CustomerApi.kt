package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CustomerApi {

    @GET("customers")
    fun getCustomers(): Call<ResponseCustomer>

    @POST("customers")
    fun storeCustomer(@Body params: RequestBody): Call<ResponseStoreCustomer>

    @PUT("customers/{id}")
    fun updateCustomer(@Path("id") id: String, @Body params: RequestBody): Call<ResponseUpdateCustomer>

    @DELETE("customers/{id}")
    fun deleteCustomer(@Path("id") id: String): Call<ResponseDeleteCustomer>
}