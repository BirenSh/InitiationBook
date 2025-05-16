package com.example.initiations.di.repositories

import android.content.SharedPreferences
import com.example.initiations.util.AppConstant.PreFranceKey.LOGGED_IN
import javax.inject.Inject

class SharePrefRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setIsLoggedIn(value: Boolean) {
        sharedPreferences.edit().putBoolean(LOGGED_IN, value).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN,false)
    }


}