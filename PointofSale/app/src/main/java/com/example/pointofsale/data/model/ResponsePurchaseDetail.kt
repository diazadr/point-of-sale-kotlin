package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponsePurchaseDetail(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: List<PurchaseDetail>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class PurchaseDetail(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("purchase_id")
    val purchaseId: Int? = null,

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
