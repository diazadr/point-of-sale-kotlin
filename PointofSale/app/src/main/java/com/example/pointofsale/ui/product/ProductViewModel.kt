package com.example.pointofsale.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Product
import com.example.pointofsale.data.model.ResponseProduct
import com.example.pointofsale.data.repository.ProductRepository
import java.io.File

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    val storeSuccess = MutableLiveData<Boolean>()
    val updateSuccess = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()

    val products: LiveData<ResponseProduct> = repository.products
    val error: LiveData<String> = repository.error

    fun loadProducts() {
        repository.fetchProducts()
    }

    fun storeProduct(product: Product, imageFile: File?) {
        repository.storeProduct(product, imageFile) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun updateProduct(product: Product, imageFile: File?) {
        repository.updateProduct(product, imageFile) { success ->
            updateSuccess.postValue(success)
        }
    }

    fun deleteProduct(id: Int) {
        repository.deleteProduct(id) { success ->
            deleteSuccess.postValue(success)
        }
    }
}
