package com.example.pointofsale.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SalesReportRepository {

    private val _salesByProduct = MutableLiveData<ResponseSalesReport<List<SalesReportByProduct>>>()
    val salesByProduct: LiveData<ResponseSalesReport<List<SalesReportByProduct>>> = _salesByProduct

    private val _salesSummary = MutableLiveData<ResponseSalesReport<List<SalesReportSummary>>>()
    val salesSummary: LiveData<ResponseSalesReport<List<SalesReportSummary>>> = _salesSummary

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchSalesByProduct() {
        val client = ApiConfig.salesReportApi.getSalesReportByProduct()
        client.enqueue(object : Callback<ResponseSalesReport<List<SalesReportByProduct>>> {
            override fun onResponse(
                call: Call<ResponseSalesReport<List<SalesReportByProduct>>>,
                response: Response<ResponseSalesReport<List<SalesReportByProduct>>>
            ) {
                if (response.isSuccessful) {
                    _salesByProduct.postValue(response.body())
                } else {
                    _error.postValue("Gagal memuat laporan per produk")
                }
            }

            override fun onFailure(
                call: Call<ResponseSalesReport<List<SalesReportByProduct>>>,
                t: Throwable
            ) {
                _error.postValue(t.message)
            }
        })
    }

    fun fetchSalesSummary() {
        val client = ApiConfig.salesReportApi.getSalesReportSummary()
        client.enqueue(object : Callback<ResponseSalesReport<List<SalesReportSummary>>> {
            override fun onResponse(
                call: Call<ResponseSalesReport<List<SalesReportSummary>>>,
                response: Response<ResponseSalesReport<List<SalesReportSummary>>>
            ) {
                if (response.isSuccessful) {
                    _salesSummary.postValue(response.body())
                } else {
                    _error.postValue("Gagal memuat ringkasan laporan")
                }
            }

            override fun onFailure(
                call: Call<ResponseSalesReport<List<SalesReportSummary>>>,
                t: Throwable
            ) {
                _error.postValue(t.message)
            }
        })
    }
}
