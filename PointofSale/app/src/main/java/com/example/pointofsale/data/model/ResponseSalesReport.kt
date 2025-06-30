package com.example.pointofsale.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.math.BigDecimal


@Parcelize
data class ResponseSalesReport<T>(
    @SerializedName("status")
    val status: Int? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("payload")
    val payload: @RawValue T? = null,

    @SerializedName("metadata")
    val metadata: Metadata? = null
) : Parcelable

@Parcelize
data class SalesReportSummary(
    @SerializedName("report_period")
    val reportPeriod: String? = null,

    @SerializedName("total_revenue")
    val totalRevenue: BigDecimal? = null,

    @SerializedName("total_transactions")
    val totalTransactions: Int? = null,

    @SerializedName("best_selling_product")
    val bestSellingProduct: String? = null
) : Parcelable

@Parcelize
data class SalesReportByProduct(
    @SerializedName("product_name")
    val productName: String? = null,

    @SerializedName("total_quantity")
    val totalQuantity: Int? = null,

    @SerializedName("total_sales")
    val totalSales: BigDecimal? = null
) : Parcelable
