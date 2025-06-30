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

class SupplierRepository {

    private val _suppliers = MutableLiveData<ResponseSupplier>()
    val suppliers: LiveData<ResponseSupplier> = _suppliers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchSuppliers() {
        val client = ApiConfig.supplierApi.getSuppliers()
        client.enqueue(object : Callback<ResponseSupplier> {
            override fun onResponse(call: Call<ResponseSupplier>, response: Response<ResponseSupplier>) {
                if (response.isSuccessful) {
                    _suppliers.postValue(response.body())
                } else {
                    _error.postValue("Terjadi kesalahan saat memuat supplier")
                }
            }

            override fun onFailure(call: Call<ResponseSupplier>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeSupplier(supplier: Supplier, callback: (Boolean) -> Unit) {
        val requestBody = createSupplierRequestBody(supplier)
        val call = ApiConfig.supplierApi.storeSupplier(requestBody)

        call.enqueue(object : Callback<ResponseStoreSupplier> {
            override fun onResponse(call: Call<ResponseStoreSupplier>, response: Response<ResponseStoreSupplier>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseStoreSupplier>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun updateSupplier(supplier: Supplier, callback: (Boolean) -> Unit) {
        val requestBody = createSupplierRequestBody(supplier)
        val call = ApiConfig.supplierApi.updateSupplier(supplier.id.toString(), requestBody)

        call.enqueue(object : Callback<ResponseUpdateSupplier> {
            override fun onResponse(call: Call<ResponseUpdateSupplier>, response: Response<ResponseUpdateSupplier>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseUpdateSupplier>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun deleteSupplier(id: Int, callback: (Boolean) -> Unit) {
        val call = ApiConfig.supplierApi.deleteSupplier(id.toString())
        call.enqueue(object : Callback<ResponseDeleteSupplier> {
            override fun onResponse(call: Call<ResponseDeleteSupplier>, response: Response<ResponseDeleteSupplier>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DeleteSupplier", "Gagal hapus: $errorBody")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseDeleteSupplier>, t: Throwable) {
                Log.e("DeleteSupplier", "Gagal hapus supplier: ${t.message}", t)
                callback(false)
            }
        })
    }

    private fun createSupplierRequestBody(supplier: Supplier): RequestBody {
        val jsonObject = JSONObject().apply {
            put("name", supplier.name)
            put("phone", supplier.phone)
            put("email", supplier.email)
            put("address", supplier.address)
            put("supplied_product", supplier.suppliedProduct)
        }

        val jsonString = jsonObject.toString()
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}