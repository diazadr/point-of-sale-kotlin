package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseCategory(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: List<Category>? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseStoreCategory(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Category? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseUpdateCategory(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Category? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseDeleteCategory(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: Category? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Category(
	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("description")
	val description: String? = null
) : Parcelable
