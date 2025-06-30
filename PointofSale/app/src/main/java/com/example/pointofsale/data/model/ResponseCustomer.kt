package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseCustomer(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: List<Customer>? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseStoreCustomer(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Customer? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseUpdateCustomer(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Customer? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseDeleteCustomer(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Customer? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Customer(
	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("address")
	val address: String? = null,

	@SerializedName("customer_type")
	val customerType: String? = null
) : Parcelable