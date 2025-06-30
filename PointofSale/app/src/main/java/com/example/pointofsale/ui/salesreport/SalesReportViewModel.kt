package com.example.pointofsale.ui.salesreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.*
import com.example.pointofsale.data.repository.SalesReportRepository

class SalesReportViewModel : ViewModel() {

    private val repository = SalesReportRepository()

    val salesSummary: LiveData<ResponseSalesReport<List<SalesReportSummary>>> = repository.salesSummary
    val salesByProduct: LiveData<ResponseSalesReport<List<SalesReportByProduct>>> = repository.salesByProduct
    val error: LiveData<String> = repository.error

    fun fetchSalesByProduct() {
        repository.fetchSalesByProduct()
    }
    fun loadSalesSummary() {
        repository.fetchSalesSummary()
    }

    fun loadSalesByProduct() {
        repository.fetchSalesByProduct()
    }

    fun fetchSalesSummary() {
        repository.fetchSalesSummary()
    }



}
