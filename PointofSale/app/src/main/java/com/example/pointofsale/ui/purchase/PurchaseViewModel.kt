package com.example.pointofsale.ui.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Purchase
import com.example.pointofsale.data.model.ResponsePurchase
import com.example.pointofsale.data.model.ResponsePurchaseDetail
import com.example.pointofsale.data.repository.PurchaseRepository

class PurchaseViewModel : ViewModel() {

    private val repository = PurchaseRepository()
    val storeSuccess = MutableLiveData<Boolean>()

    val purchases: LiveData<ResponsePurchase> = repository.purchases
    val purchaseDetails: LiveData<ResponsePurchaseDetail> = repository.purchaseDetails
    val error: LiveData<String> = repository.error

    fun loadPurchases() {
        repository.fetchPurchases()
    }

    fun loadPurchaseDetails(purchaseId: String) {
        repository.fetchPurchaseDetails(purchaseId)
    }

    fun storePurchase(purchase: Purchase) {
        repository.storePurchase(purchase) { success ->
            storeSuccess.postValue(success)
        }
    }
}
