package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.ResponseSalesReport
import com.example.pointofsale.data.model.SalesReportByProduct
import com.example.pointofsale.data.model.SalesReportSummary
import retrofit2.Call
import retrofit2.http.GET

interface SalesReportApi {

    @GET("sales-reports/by-product")
    fun getSalesReportByProduct(): Call<ResponseSalesReport<List<SalesReportByProduct>>>

    @GET("sales-reports/summary")
    fun getSalesReportSummary(): Call<ResponseSalesReport<List<SalesReportSummary>>>
}
