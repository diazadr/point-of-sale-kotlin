package com.example.pointofsale.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseUser(
	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("payload")
	val payload: List<User>? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable


@Parcelize
data class ResponseUserSingle(
	@SerializedName("metadata") val metadata: Metadata? = null,
	@SerializedName("payload") val payload: User? = null,
	@SerializedName("message") val message: String? = null,
	@SerializedName("status") val status: Int? = null
) : Parcelable

@Parcelize
data class User(
	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("role")
	val role: String? = null,

	@SerializedName("phone")
	val phone: String? = null
) : Parcelable
