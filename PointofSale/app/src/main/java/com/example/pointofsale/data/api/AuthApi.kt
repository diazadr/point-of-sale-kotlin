package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.LoginRequest
import com.example.pointofsale.data.model.RegisterRequest
import com.example.pointofsale.data.model.ResponseLogin
import com.example.pointofsale.data.model.ResponseUserSingle
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<ResponseLogin>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<ResponseUserSingle>
}
