package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Purchase(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("supplier_id")
    val supplierId: Int? = null,

    @SerializedName("total_payment")
    val totalPayment: Double? = null,

    @SerializedName("supplier_name")
    val supplierName: String? = null, // ← Tambahkan ini

    @SerializedName("items")
    val items: List<PurchaseDetail>? = null
) : Parcelable


@Parcelize
data class ResponsePurchase(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: List<Purchase>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null

) : Parcelable

@Parcelize
data class ResponseStorePurchase(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: Purchase? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable
