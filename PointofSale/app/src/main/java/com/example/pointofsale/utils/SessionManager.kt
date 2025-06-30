package com.example.pointofsale.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefsName = "pos_app_prefs"
    private val authTokenKey = "AUTH_TOKEN"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(authTokenKey, token).apply()
    }

    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(authTokenKey, null)
    }

    fun saveUserRole(role: String) {
        sharedPreferences.edit().putString("USER_ROLE", role).apply()
    }

    fun fetchUserRole(): String? {
        return sharedPreferences.getString("USER_ROLE", null)
    }

    fun saveUserType(userType: String) {
        sharedPreferences.edit().putString("USER_TYPE", userType).apply()
    }

    fun fetchUserType(): String? {
        return sharedPreferences.getString("USER_TYPE", null)
    }

    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
