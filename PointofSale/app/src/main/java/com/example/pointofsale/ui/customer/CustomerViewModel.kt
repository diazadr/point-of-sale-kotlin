package com.example.pointofsale.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Customer
import com.example.pointofsale.data.model.ResponseCustomer
import com.example.pointofsale.data.repository.CustomerRepository

class CustomerViewModel : ViewModel() {

    private val repository = CustomerRepository()
    val storeSuccess = MutableLiveData<Boolean>()
    val updateSuccess = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()

    val customers: LiveData<ResponseCustomer> = repository.customers
    val error: LiveData<String> = repository.error

    fun loadCustomers() {
        repository.fetchCustomers()
    }

    fun storeCustomer(customer: Customer) {
        repository.storeCustomer(customer) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun updateCustomer(customer: Customer) {
        repository.updateCustomer(customer) { success ->
            updateSuccess.postValue(success)
        }
    }

    fun deleteCustomer(id: Int) {
        repository.deleteCustomer(id) { success ->
            deleteSuccess.postValue(success)
        }
    }
}