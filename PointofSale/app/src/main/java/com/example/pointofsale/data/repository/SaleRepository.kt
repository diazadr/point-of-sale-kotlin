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

class SaleRepository {

    private val _sales = MutableLiveData<ResponseSale>()
    val sales: LiveData<ResponseSale> = _sales

    private val _saleDetails = MutableLiveData<ResponseSaleDetail>()
    val saleDetails: LiveData<ResponseSaleDetail> = _saleDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _sale = MutableLiveData<ResponseStoreSale>()
    val sale: LiveData<ResponseStoreSale> = _sale


    fun fetchSales() {
        val client = ApiConfig.saleApi.getSales()
        client.enqueue(object : Callback<ResponseSale> {
            override fun onResponse(call: Call<ResponseSale>, response: Response<ResponseSale>) {
                if (response.isSuccessful) {
                    _sales.postValue(response.body())
                } else {
                    _error.postValue("Failed to load sales")
                }
            }

            override fun onFailure(call: Call<ResponseSale>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun fetchSaleDetails(transactionId: String) {
        val client = ApiConfig.saleApi.getSaleDetails(transactionId)
        client.enqueue(object : Callback<ResponseSaleDetail> {
            override fun onResponse(call: Call<ResponseSaleDetail>, response: Response<ResponseSaleDetail>) {
                if (response.isSuccessful) {
                    _saleDetails.postValue(response.body())
                } else {
                    _error.postValue("Failed to load sale details")
                }
            }

            override fun onFailure(call: Call<ResponseSaleDetail>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun fetchSaleById(transactionId: String) {
        val call = ApiConfig.saleApi.getSaleById(transactionId)
        call.enqueue(object : Callback<ResponseStoreSale> {
            override fun onResponse(call: Call<ResponseStoreSale>, response: Response<ResponseStoreSale>) {
                if (response.isSuccessful) {
                    _sale.postValue(response.body())
                } else {
                    _error.postValue("Failed to load sale")
                }
            }

            override fun onFailure(call: Call<ResponseStoreSale>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeSale(sale: Sale, callback: (Boolean) -> Unit) {
        val requestBody = createSaleRequestBody(sale)
        val call = ApiConfig.saleApi.storeSale(requestBody)

        call.enqueue(object : Callback<ResponseStoreSale> {
            override fun onResponse(call: Call<ResponseStoreSale>, response: Response<ResponseStoreSale>) {
                if (!response.isSuccessful) {
                    Log.e("storeSale", "Failed: ${response.code()} - ${response.errorBody()?.string()}")
                }
                callback(response.isSuccessful)
            }


            override fun onFailure(call: Call<ResponseStoreSale>, t: Throwable) {
                callback(false)
            }
        })
    }

    private fun createSaleRequestBody(sale: Sale): RequestBody {
        val jsonObject = JSONObject().apply {
            put("customer_id", sale.customerId)
            put("total_price", sale.totalPrice)
            put("payment_method", sale.paymentMethod)
            put("transaction_date", sale.transactionDate) // ✅ TAMBAHKAN INI

            val itemsArray = org.json.JSONArray()
            sale.items?.forEach { item ->
                val itemObject = JSONObject().apply {
                    put("product_id", item.productId)
                    put("quantity", item.quantity)
                    put("unit_price", item.unitPrice)
                    put("subtotal", item.subtotal)
                }
                itemsArray.put(itemObject)
            }

            put("items", itemsArray)
        }

        val jsonString = jsonObject.toString()
        Log.d("SaleRequest", jsonString) // Debug output
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }

}