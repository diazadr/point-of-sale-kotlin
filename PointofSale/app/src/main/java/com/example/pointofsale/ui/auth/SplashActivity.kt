package com.example.pointofsale.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pointofsale.utils.SessionManager
import com.example.pointofsale.NavActivity
import com.example.pointofsale.data.api.ApiConfig
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        ApiConfig.init(applicationContext)
        val isLoggedIn = sessionManager.fetchAuthToken() != null

        val nextIntent = if (isLoggedIn) {
            Intent(this, NavActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(nextIntent)
        finish()
    }
}