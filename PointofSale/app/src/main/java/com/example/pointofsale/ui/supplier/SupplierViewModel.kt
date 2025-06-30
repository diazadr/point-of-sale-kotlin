package com.example.pointofsale.ui.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Supplier
import com.example.pointofsale.data.model.ResponseSupplier
import com.example.pointofsale.data.repository.SupplierRepository

class SupplierViewModel : ViewModel() {

    private val repository = SupplierRepository()
    val storeSuccess = MutableLiveData<Boolean>()
    val updateSuccess = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()

    val suppliers: LiveData<ResponseSupplier> = repository.suppliers
    val error: LiveData<String> = repository.error

    fun loadSuppliers() {
        repository.fetchSuppliers()
    }

    fun storeSupplier(supplier: Supplier) {
        repository.storeSupplier(supplier) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun updateSupplier(supplier: Supplier) {
        repository.updateSupplier(supplier) { success ->
            updateSuccess.postValue(success)
        }
    }

    fun deleteSupplier(id: Int) {
        repository.deleteSupplier(id) { success ->
            deleteSuccess.postValue(success)
        }
    }
}