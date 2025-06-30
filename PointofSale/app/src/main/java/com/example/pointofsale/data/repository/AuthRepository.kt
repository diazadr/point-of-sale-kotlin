package com.example.pointofsale.data.repository

import android.util.Log
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.LoginRequest
import com.example.pointofsale.data.model.RegisterRequest
import com.example.pointofsale.data.model.ResponseLogin
import com.example.pointofsale.data.model.ResponseUserSingle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    private val authApi = ApiConfig.authApi

    fun login(
        request: LoginRequest,
        onSuccess: (ResponseLogin) -> Unit,
        onError: (String) -> Unit
    ) {
        authApi.login(request).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    val message = response.errorBody()?.string() ?: "Login gagal"
                    Log.e("AuthRepository", "Login error: $message")
                    onError(message)
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                val message = t.message ?: "Terjadi kesalahan jaringan"
                Log.e("AuthRepository", "Login failure: $message", t)
                onError(message)
            }
        })
    }

    fun register(
        request: RegisterRequest,
        onSuccess: (ResponseUserSingle) -> Unit,
        onError: (String) -> Unit
    ) {
        authApi.register(request).enqueue(object : Callback<ResponseUserSingle> {
            override fun onResponse(call: Call<ResponseUserSingle>, response: Response<ResponseUserSingle>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    val message = response.errorBody()?.string() ?: "Registrasi gagal"
                    Log.e("AuthRepository", "Register error: $message")
                    onError(message)
                }
            }

            override fun onFailure(call: Call<ResponseUserSingle>, t: Throwable) {
                val message = t.message ?: "Terjadi kesalahan jaringan"
                Log.e("AuthRepository", "Register failure: $message", t)
                onError(message)
            }
        })
    }
}
