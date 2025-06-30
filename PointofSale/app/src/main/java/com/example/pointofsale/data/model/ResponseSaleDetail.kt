package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseSaleDetail(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: List<SaleDetail>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class SaleDetail(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("transaction_id")
    val transactionId: Int? = null,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("unit_price")
    val unitPrice: Double? = null,

    @SerializedName("subtotal")
    val subtotal: Double? = null,

    @SerializedName("product_name")
    val productName: String? = null
) : Parcelable