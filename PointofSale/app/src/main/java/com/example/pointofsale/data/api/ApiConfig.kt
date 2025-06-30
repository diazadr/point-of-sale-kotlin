package com.example.pointofsale.data.api

import android.content.Context
import android.util.Log
import com.example.pointofsale.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = "http://172.16.7.133:3000/api/"
    private lateinit var sessionManager: SessionManager

    fun init(context: Context) {
        sessionManager = SessionManager(context)
    }

    private val authInterceptor = Interceptor { chain ->
        val token = sessionManager.fetchAuthToken()
        Log.d("ApiTokenDebug", "Auth token: $token")
        val request: Request = if (!token.isNullOrEmpty()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        chain.proceed(request)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApi: ProductApi by lazy { retrofit.create(ProductApi::class.java) }
    val categoryApi: CategoryApi by lazy { retrofit.create(CategoryApi::class.java) }
    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val customerApi: CustomerApi by lazy { retrofit.create(CustomerApi::class.java) }
    val supplierApi: SupplierApi by lazy { retrofit.create(SupplierApi::class.java) }
    val saleApi: SaleApi by lazy { retrofit.create(SaleApi::class.java) }
    val purchaseApi: PurchaseApi by lazy { retrofit.create(PurchaseApi::class.java) }
    val salesReportApi: SalesReportApi by lazy { retrofit.create(SalesReportApi::class.java) }
    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
}
