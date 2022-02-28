package com.fiap.wallet.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wallet.models.Session

class SharedSession(context: Context) {

    private val PREFS_NAME = "userSession"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)

    fun setStr(key: String?, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setBool(key: String?, value: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStr(key: String?): String? {
        return preferences.getString(key, "")
    }

    fun getBool(key: String?): Boolean? {
        return preferences.getBoolean(key, false)
    }

    fun clearAll() {
        preferences.edit().clear().commit()
    }

    fun getSession(): Session {
        val cpf = this.getStr("cpf").toString()
        val token = this.getStr("token").toString()
        return Session(cpf, "Bearer $token")
    }

}
