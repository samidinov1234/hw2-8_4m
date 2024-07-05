package com.example.zametka_1_4m.data.pref

import android.content.Context

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun showed() {
        pref.edit().putBoolean(SHOWED, true).apply()
    }

    fun isShowed(): Boolean {
        return pref.getBoolean(SHOWED, false)
    }


    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED = "showed"
    }
}
