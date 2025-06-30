package com.example.pointofsale.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseRepository {

    private val _purchases = MutableLiveData<ResponsePurchase>()
    val purchases: LiveData<ResponsePurchase> = _purchases

    private val _purchaseDetails = MutableLiveData<ResponsePurchaseDetail>()
    val purchaseDetails: LiveData<ResponsePurchaseDetail> = _purchaseDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchPurchases() {
        val client = ApiConfig.purchaseApi.getPurchases()
        client.enqueue(object : Callback<ResponsePurchase> {
            override fun onResponse(call: Call<ResponsePurchase>, response: Response<ResponsePurchase>) {
                if (response.isSuccessful) {
                    _purchases.postValue(response.body())
                } else {
                    _error.postValue("Failed to load purchases")
                }
            }

            override fun onFailure(call: Call<ResponsePurchase>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun fetchPurchaseDetails(purchaseId: String) {
        val client = ApiConfig.purchaseApi.getPurchaseDetails(purchaseId)
        client.enqueue(object : Callback<ResponsePurchaseDetail> {
            override fun onResponse(call: Call<ResponsePurchaseDetail>, response: Response<ResponsePurchaseDetail>) {
                if (response.isSuccessful) {
                    _purchaseDetails.postValue(response.body())
                } else {
                    _error.postValue("Failed to load purchase details")
                }
            }

            override fun onFailure(call: Call<ResponsePurchaseDetail>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storePurchase(purchase: Purchase, callback: (Boolean) -> Unit) {
        val requestBody = createPurchaseRequestBody(purchase)
        val call = ApiConfig.purchaseApi.storePurchase(requestBody)

        call.enqueue(object : Callback<ResponseStorePurchase> {
            override fun onResponse(call: Call<ResponseStorePurchase>, response: Response<ResponseStorePurchase>) {
                if (!response.isSuccessful) {
                    Log.e("storePurchase", "Failed: ${response.code()} - ${response.errorBody()?.string()}")
                }
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseStorePurchase>, t: Throwable) {
                callback(false)
            }
        })
    }

    private fun createPurchaseRequestBody(purchase: Purchase): RequestBody {
        val jsonObject = JSONObject().apply {
            put("date", purchase.date)
            put("supplier_id", purchase.supplierId)
            put("total_payment", purchase.totalPayment)

            val itemsArray = JSONArray()
            purchase.items?.forEach { item ->
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
        Log.d("PurchaseRequest", jsonString)
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}
