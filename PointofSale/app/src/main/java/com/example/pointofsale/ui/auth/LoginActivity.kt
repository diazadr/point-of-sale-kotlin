package com.example.pointofsale.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pointofsale.NavActivity
import com.example.pointofsale.data.api.ApiConfig
import com.example.pointofsale.databinding.ActivityLoginBinding
import com.example.pointofsale.utils.SessionManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loginResponse.observe(this) { response ->
            response.payload?.let { payload ->

                sessionManager.saveAuthToken(payload.token ?: "")
                sessionManager.saveUserRole(payload.role ?: "")
                sessionManager.saveUserType(payload.userType ?: "")

                Log.d("LoginDebug", "Token: ${payload.token}")
                Log.d("LoginDebug", "Role: ${payload.role}")
                Log.d("LoginDebug", "UserType: ${payload.userType}")

                ApiConfig.init(applicationContext)

                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, NavActivity::class.java))
                finish()
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}
