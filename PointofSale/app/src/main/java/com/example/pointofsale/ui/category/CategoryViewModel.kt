package com.example.pointofsale.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.Category
import com.example.pointofsale.data.model.ResponseCategory
import com.example.pointofsale.data.repository.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()
    val storeSuccess = MutableLiveData<Boolean>()
    val updateSuccess = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()

    val categories: LiveData<ResponseCategory> = repository.categories
    val error: LiveData<String> = repository.error

    fun loadCategories() {
        repository.fetchCategories()
    }

    fun storeCategory(category: Category) {
        repository.storeCategory(category) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun updateCategory(category: Category) {
        repository.updateCategory(category) { success ->
            updateSuccess.postValue(success)
        }
    }

    fun deleteCategory(id: Int) {
        repository.deleteCategory(id) { success ->
            deleteSuccess.postValue(success)
        }
    }
}
