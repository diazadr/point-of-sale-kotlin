package com.example.pointofsale.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.ResponseUser
import com.example.pointofsale.data.model.ResponseUserSingle
import com.example.pointofsale.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val userApi = ApiConfig.userApi

    private val _users = MutableLiveData<ResponseUser>()
    val users: LiveData<ResponseUser> = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchUsers() {
        userApi.getUsers().enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    _users.postValue(response.body())
                } else {
                    _error.postValue("Gagal memuat data user")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeUser(user: User, callback: (Boolean) -> Unit) {
        userApi.storeUser(user).enqueue(object : Callback<ResponseUserSingle> {
            override fun onResponse(call: Call<ResponseUserSingle>, response: Response<ResponseUserSingle>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    Log.e("UserRepository", "Store user failed: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseUserSingle>, t: Throwable) {
                Log.e("UserRepository", "Store user error: ${t.message}", t)
                callback(false)
            }
        })
    }


    fun updateUser(user: User, callback: (Boolean) -> Unit) {
        val id = user.id ?: return callback(false)
        userApi.updateUser(id, user).enqueue(object : Callback<ResponseUserSingle> {
            override fun onResponse(call: Call<ResponseUserSingle>, response: Response<ResponseUserSingle>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    Log.e("UserRepository", "Update user failed: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseUserSingle>, t: Throwable) {
                Log.e("UserRepository", "Update user error: ${t.message}", t)
                callback(false)
            }
        })
    }


    fun deleteUser(id: Int, callback: (Boolean) -> Unit) {
        userApi.deleteUser(id).enqueue(object : Callback<ResponseUserSingle> {
            override fun onResponse(call: Call<ResponseUserSingle>, response: Response<ResponseUserSingle>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    Log.e("UserRepository", "Delete user failed: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseUserSingle>, t: Throwable) {
                Log.e("UserRepository", "Delete user error: ${t.message}", t)
                callback(false)
            }
        })
    }

}
