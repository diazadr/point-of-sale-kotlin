package com.example.pointofsale.ui.sale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Sale
import com.example.pointofsale.data.model.ResponseSale
import com.example.pointofsale.data.model.ResponseSaleDetail
import com.example.pointofsale.data.model.ResponseStoreSale
import com.example.pointofsale.data.repository.SaleRepository

class SaleViewModel : ViewModel() {

    private val repository = SaleRepository()
    val storeSuccess = MutableLiveData<Boolean>()

    val sales: LiveData<ResponseSale> = repository.sales
    val saleDetails: LiveData<ResponseSaleDetail> = repository.saleDetails
    val error: LiveData<String> = repository.error
    val sale: LiveData<ResponseStoreSale> = repository.sale

    fun loadSales() {
        repository.fetchSales()
    }

    fun loadSaleDetails(transactionId: String) {
        repository.fetchSaleDetails(transactionId)
    }

    fun storeSale(sale: Sale) {
        repository.storeSale(sale) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun loadSale(transactionId: String) {
        repository.fetchSaleById(transactionId)
    }
}