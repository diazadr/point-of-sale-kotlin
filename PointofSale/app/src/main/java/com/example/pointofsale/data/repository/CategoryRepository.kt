package com.example.pointofsale.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {

    private val _categories = MutableLiveData<ResponseCategory>()
    val categories: LiveData<ResponseCategory> = _categories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCategories() {
        val client = ApiConfig.categoryApi.getCategory()
        client.enqueue(object : Callback<ResponseCategory> {
            override fun onResponse(call: Call<ResponseCategory>, response: Response<ResponseCategory>) {
                if (response.isSuccessful) {
                    _categories.postValue(response.body())
                } else {
                    _error.postValue("Terjadi kesalahan saat memuat kategori")
                }
            }

            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeCategory(category: Category, callback: (Boolean) -> Unit) {
        val requestBody = createCategoryRequestBody(category)
        val call = ApiConfig.categoryApi.storeCategory(requestBody)

        call.enqueue(object : Callback<ResponseStoreCategory> {
            override fun onResponse(call: Call<ResponseStoreCategory>, response: Response<ResponseStoreCategory>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseStoreCategory>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun updateCategory(category: Category, callback: (Boolean) -> Unit) {
        val requestBody = createCategoryRequestBody(category)
        val call = ApiConfig.categoryApi.updateCategory(category.id.toString(), requestBody)

        call.enqueue(object : Callback<ResponseUpdateCategory> {
            override fun onResponse(call: Call<ResponseUpdateCategory>, response: Response<ResponseUpdateCategory>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseUpdateCategory>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun deleteCategory(id: Int, callback: (Boolean) -> Unit) {
        val call = ApiConfig.categoryApi.deleteCategory(id.toString())
        call.enqueue(object : Callback<ResponseDeleteCategory> {
            override fun onResponse(call: Call<ResponseDeleteCategory>, response: Response<ResponseDeleteCategory>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DeleteCategory", "Gagal hapus: $errorBody")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseDeleteCategory>, t: Throwable) {
                Log.e("DeleteCategory", "Gagal hapus kategori: ${t.message}", t)
                callback(false)
            }
        })
    }

    private fun createCategoryRequestBody(category: Category): RequestBody {
        val jsonObject = JSONObject().apply {
            put("name", category.name)
            put("description", category.description)
        }

        val jsonString = jsonObject.toString()
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}
