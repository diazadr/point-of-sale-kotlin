package com.example.pointofsale.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.User
import com.example.pointofsale.data.model.ResponseUser
import com.example.pointofsale.data.repository.UserRepository

class UserViewModel : ViewModel() {

    private val repository = UserRepository()
    val storeSuccess = MutableLiveData<Boolean>()
    val updateSuccess = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()

    val users: LiveData<ResponseUser> = repository.users
    val error: LiveData<String> = repository.error

    fun loadUsers() {
        repository.fetchUsers()
    }

    fun storeUser(user: User) {
        repository.storeUser(user) { success ->
            storeSuccess.postValue(success)
        }
    }

    fun updateUser(user: User) {
        repository.updateUser(user) { success ->
            updateSuccess.postValue(success)
        }
    }

    fun deleteUser(id: Int) {
        repository.deleteUser(id) { success ->
            deleteSuccess.postValue(success)
        }
    }
}
