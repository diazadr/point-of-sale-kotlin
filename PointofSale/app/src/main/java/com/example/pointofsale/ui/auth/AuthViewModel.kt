package com.example.pointofsale.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointofsale.data.model.LoginRequest
import com.example.pointofsale.data.model.ResponseLogin
import com.example.pointofsale.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _loginResponse = MutableLiveData<ResponseLogin>()
    val loginResponse: LiveData<ResponseLogin> = _loginResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        _isLoading.postValue(true)
        val request = LoginRequest(username, password)
        authRepository.login(
            request,
            onSuccess = { response ->
                _isLoading.postValue(false)
                _loginResponse.postValue(response)
            },
            onError = { errorMessage ->
                _isLoading.postValue(false)
                _error.postValue(errorMessage)
            }
        )
    }
}
