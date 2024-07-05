package com.example.zametka_1_4m.data.pref

import android.content.Context
import com.example.zametka_1_4m.utils.CONSTANTS

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun showed() {
        pref.edit().putBoolean(SHOWED, true).apply()
    }

    fun isShowed(): Boolean {
        return pref.getBoolean(SHOWED, false)
    }
    fun setUserAuth(bool: Boolean) {
        pref
            .edit()
            .putBoolean(CONSTANTS.USER_AUTHED, bool)
            .apply()
    }

    fun getUserAuthed(): Boolean {
        return pref.getBoolean(CONSTANTS.USER_AUTHED, false)
    }
    fun saveName(name: String) {
        pref
            .edit()
            .putString(CONSTANTS.PROFILE_NAME, name)
            .apply()
    }

    fun getSavedName(): String? {
        val savedName = pref.getString(CONSTANTS.PROFILE_NAME, "default")

        return if (savedName != "default") {
            savedName
        } else {
            null
        }
    }

    fun saveLogin(login: String) {
        pref
            .edit()
            .putString(CONSTANTS.PROFILE_LOGIN, login)
            .apply()
    }

    fun getSavedLogin(): String? {
        val savedLogin = pref.getString(CONSTANTS.PROFILE_LOGIN, "default")

        return if (savedLogin != "default") {
            savedLogin
        } else {
            null
        }
    }

    fun saveAvatar(url: String) {
        pref
            .edit()
            .putString(CONSTANTS.PROFILE_AVATAR, url)
            .apply()
    }

    fun getSavedAvatar(): String? {
        val savedAvatar = pref.getString(CONSTANTS.PROFILE_AVATAR, "default")

        return if (savedAvatar != "default") {
            savedAvatar
        } else {
            null
        }
    }

    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED = "showed"
    }
}
