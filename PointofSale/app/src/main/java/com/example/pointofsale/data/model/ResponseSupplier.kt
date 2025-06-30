package com.example.pointofsale.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseSupplier(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: List<Supplier>? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseStoreSupplier(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Supplier? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseUpdateSupplier(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Supplier? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseDeleteSupplier(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Supplier? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Supplier(
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

	@SerializedName("supplied_product")
	val suppliedProduct: String? = null
) : Parcelable