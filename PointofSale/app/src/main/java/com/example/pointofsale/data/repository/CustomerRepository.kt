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

class CustomerRepository {

    private val _customers = MutableLiveData<ResponseCustomer>()
    val customers: LiveData<ResponseCustomer> = _customers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCustomers() {
        val client = ApiConfig.customerApi.getCustomers()
        client.enqueue(object : Callback<ResponseCustomer> {
            override fun onResponse(call: Call<ResponseCustomer>, response: Response<ResponseCustomer>) {
                if (response.isSuccessful) {
                    _customers.postValue(response.body())
                } else {
                    _error.postValue("Failed to load customers")
                }
            }

            override fun onFailure(call: Call<ResponseCustomer>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeCustomer(customer: Customer, callback: (Boolean) -> Unit) {
        val requestBody = createCustomerRequestBody(customer)
        val call = ApiConfig.customerApi.storeCustomer(requestBody)

        call.enqueue(object : Callback<ResponseStoreCustomer> {
            override fun onResponse(
                call: Call<ResponseStoreCustomer>,
                response: Response<ResponseStoreCustomer>
            ) {
                if (response.isSuccessful) {
                    fetchCustomers() // Refresh the list
                }
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseStoreCustomer>, t: Throwable) {
                _error.postValue(t.message)
                callback(false)
            }
        })
    }

    fun updateCustomer(customer: Customer, callback: (Boolean) -> Unit) {
        val requestBody = createCustomerRequestBody(customer)
        val call = ApiConfig.customerApi.updateCustomer(customer.id.toString(), requestBody)

        call.enqueue(object : Callback<ResponseUpdateCustomer> {
            override fun onResponse(
                call: Call<ResponseUpdateCustomer>,
                response: Response<ResponseUpdateCustomer>
            ) {
                if (response.isSuccessful) {
                    fetchCustomers() // Refresh the list
                }
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseUpdateCustomer>, t: Throwable) {
                _error.postValue(t.message)
                callback(false)
            }
        })
    }

    fun deleteCustomer(id: Int, callback: (Boolean) -> Unit) {
        val call = ApiConfig.customerApi.deleteCustomer(id.toString())
        call.enqueue(object : Callback<ResponseDeleteCustomer> {
            override fun onResponse(
                call: Call<ResponseDeleteCustomer>,
                response: Response<ResponseDeleteCustomer>
            ) {
                if (response.isSuccessful) {
                    fetchCustomers() // Refresh the list
                    callback(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DeleteCustomer", "Delete failed: $errorBody")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResponseDeleteCustomer>, t: Throwable) {
                Log.e("DeleteCustomer", "Failed to delete customer: ${t.message}", t)
                _error.postValue(t.message)
                callback(false)
            }
        })
    }

    private fun createCustomerRequestBody(customer: Customer): RequestBody {
        val jsonObject = JSONObject().apply {
            put("name", customer.name)
            put("phone", customer.phone)
            put("email", customer.email)
            put("address", customer.address)
            put("customer_type", customer.customerType)
        }

        val jsonString = jsonObject.toString()
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}