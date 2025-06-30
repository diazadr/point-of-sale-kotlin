package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.ResponseUser
import com.example.pointofsale.data.model.ResponseUserSingle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import com.example.pointofsale.data.model.User

interface UserApi {

    @GET("users")
    fun getUsers(): Call<ResponseUser>

    @POST("users")
    fun storeUser(@Body user: User): Call<ResponseUserSingle>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<ResponseUserSingle>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<ResponseUserSingle>
}
