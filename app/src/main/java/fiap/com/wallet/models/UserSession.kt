package fiap.com.wallet.models

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class UserSession (context: Context){

    private val PREFS_NAME = "userSession"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)

    fun setStr(key: String?, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStr(key: String?): String? {
        return preferences.getString(key, "")
    }

    fun clearAll(){
        preferences.edit().clear().commit()
    }


}