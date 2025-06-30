package com.example.pointofsale.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.data.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URLConnection

class ProductRepository {

    private val _products = MutableLiveData<ResponseProduct>()
    val products: LiveData<ResponseProduct> = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchProducts() {
        ApiConfig.productApi.getProduct().enqueue(object : Callback<ResponseProduct> {
            override fun onResponse(call: Call<ResponseProduct>, response: Response<ResponseProduct>) {
                if (response.isSuccessful) {
                    _products.postValue(response.body())
                } else {
                    _error.postValue("Terjadi kesalahan saat memuat produk")
                }
            }

            override fun onFailure(call: Call<ResponseProduct>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun storeProduct(product: Product, imageFile: File?, callback: (Boolean) -> Unit) {
        val (parts, imagePart) = buildProductParts(product, imageFile)

        ApiConfig.productApi.storeProduct(
            parts["name"]!!,
            parts["item_code"]!!,
            parts["category_id"]!!,
            parts["stock_quantity"]!!,
            parts["purchase_price"]!!,
            parts["selling_price"]!!,
            imagePart
        ).enqueue(object : Callback<ResponseStoreProduct> {
            override fun onResponse(call: Call<ResponseStoreProduct>, response: Response<ResponseStoreProduct>) {
                Log.d("StoreProduct", "response.isSuccessful = ${response.isSuccessful}")
                Log.d("StoreProduct", "code = ${response.code()}")
                Log.d("StoreProduct", "body = ${response.body()}")
                Log.d("StoreProduct", "errorBody = ${response.errorBody()?.string()}")
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseStoreProduct>, t: Throwable) {
                Log.e("StoreProduct", "onFailure: ${t.localizedMessage}", t)
                callback(false)
            }
        })
    }

    fun updateProduct(product: Product, imageFile: File?, callback: (Boolean) -> Unit) {
        val (parts, imagePart) = buildProductParts(product, imageFile)

        ApiConfig.productApi.updateProduct(
            product.id!!.toString(),
            parts["name"]!!,
            parts["item_code"]!!,
            parts["category_id"]!!,
            parts["stock_quantity"]!!,
            parts["purchase_price"]!!,
            parts["selling_price"]!!,
            imagePart
        ).enqueue(object : Callback<ResponseUpdateProduct> {
            override fun onResponse(call: Call<ResponseUpdateProduct>, response: Response<ResponseUpdateProduct>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseUpdateProduct>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun deleteProduct(id: Int, callback: (Boolean) -> Unit) {
        ApiConfig.productApi.deleteProduct(id.toString()).enqueue(object : Callback<ResponseDeleteProduct> {
            override fun onResponse(call: Call<ResponseDeleteProduct>, response: Response<ResponseDeleteProduct>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseDeleteProduct>, t: Throwable) {
                Log.e("DeleteProduct", "Gagal hapus produk: ${t.message}", t)
                callback(false)
            }
        })
    }

    private fun buildProductParts(product: Product, imageFile: File?): Pair<Map<String, RequestBody>, MultipartBody.Part?> {
        val mediaTypeText = "text/plain".toMediaTypeOrNull()
        val parts = mapOf(
            "name" to product.name!!.toRequestBody(mediaTypeText),
            "item_code" to product.itemCode!!.toRequestBody(mediaTypeText),
            "category_id" to product.categoryId.toString().toRequestBody(mediaTypeText),
            "stock_quantity" to product.stockQuantity.toString().toRequestBody(mediaTypeText),
            "purchase_price" to product.purchasePrice.toString().toRequestBody(mediaTypeText),
            "selling_price" to product.sellingPrice.toString().toRequestBody(mediaTypeText),
        )

        val imagePart = imageFile?.let {
            Log.d("ImageDebug", "imageFile path: ${imageFile?.absolutePath}")
            Log.d("ImageDebug", "imageFile exists: ${imageFile?.exists()}")
            Log.d("ImageDebug", "imageFile size: ${imageFile?.length()}")

            val mimeType = URLConnection.guessContentTypeFromName(it.name) ?: "image/jpeg"
            val imageRequestBody = it.asRequestBody(mimeType.toMediaTypeOrNull())
            MultipartBody.Part.createFormData("product_image", it.name, imageRequestBody)
        }

        return Pair(parts, imagePart)
    }
}
