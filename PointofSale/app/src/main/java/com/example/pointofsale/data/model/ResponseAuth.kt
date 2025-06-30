package com.example.pointofsale.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseLogin(
    @SerializedName("metadata")
    val metadata: Metadata? = null,

    @SerializedName("payload")
    val payload: LoginPayload? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class LoginPayload(
    @SerializedName("token")
    val token: String? = null,

    @SerializedName("role")
    val role: String? = null,

    @SerializedName("userType")
    val userType: String? = null
) : Parcelable

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val phone: String,
    val role: String
)
