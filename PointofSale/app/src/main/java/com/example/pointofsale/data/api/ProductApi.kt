// ProductApi.kt
package com.example.pointofsale.data.api

import com.example.pointofsale.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {

    @GET("products")
    fun getProduct(): Call<ResponseProduct>

    @Multipart
    @POST("products")
    fun storeProduct(
        @Part("name") name: RequestBody,
        @Part("item_code") itemCode: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("stock_quantity") stockQuantity: RequestBody,
        @Part("purchase_price") purchasePrice: RequestBody,
        @Part("selling_price") sellingPrice: RequestBody,
        @Part productImage: MultipartBody.Part?
    ): Call<ResponseStoreProduct>

    @Multipart
    @PUT("products/{id}")
    fun updateProduct(
        @Path("id") id: String,
        @Part("name") name: RequestBody,
        @Part("item_code") itemCode: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("stock_quantity") stockQuantity: RequestBody,
        @Part("purchase_price") purchasePrice: RequestBody,
        @Part("selling_price") sellingPrice: RequestBody,
        @Part productImage: MultipartBody.Part?
    ): Call<ResponseUpdateProduct>

    @DELETE("products/{id}")
    fun deleteProduct(@Path("id") id: String): Call<ResponseDeleteProduct>
}
