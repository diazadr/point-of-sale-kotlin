package com.example.pointofsale.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Parcelize
data class ResponseProduct(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("payload")
	val payload: List<Product>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseStoreProduct(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("payload")
	val payload: Product? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseUpdateProduct(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("payload")
	val payload: Product? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ResponseDeleteProduct(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("payload")
	val payload: Product? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Metadata(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("current")
	val current: String? = null,

	@field:SerializedName("prev")
	val prev: String? = null
) : Parcelable

@Parcelize
data class Product(

	@field:SerializedName("selling_price")
	val sellingPrice: BigDecimal? = null,

	@field:SerializedName("item_code")
	val itemCode: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("product_image")
	val productImage: String? = null,


	@field:SerializedName("product_image_url")
	val productImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("purchase_price")
	val purchasePrice: BigDecimal? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock_quantity")
	val stockQuantity: Int? = null,

	@field:SerializedName("category_name")
	val categoryName: String? = null
) : Parcelable
