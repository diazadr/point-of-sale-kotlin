package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseSale(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: List<Sale>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseStoreSale(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: Sale? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class Sale(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("transaction_date")
    val transactionDate: String? = null,

    @SerializedName("user_id")
    val userId: Int? = null,

    @SerializedName("customer_id")
    val customerId: Int? = null,

    @SerializedName("total_price")
    val totalPrice: Double? = null,

    @SerializedName("payment_method")
    val paymentMethod: String? = null,

    @SerializedName("items")
    val items: List<SaleDetail>? = null,

    @SerializedName("user_name")
val userName: String? = null,

@SerializedName("customer_name")
val customerName: String? = null

) : Parcelable