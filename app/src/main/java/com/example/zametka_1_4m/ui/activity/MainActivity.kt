package com.example.zametka_1_4m.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.data.pref.Pref

class MainActivity : AppCompatActivity() {
    private val pref by lazy {
        Pref(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController



        if (!pref.getUserAuthed()){
            navController.navigate(R.id.authFragment)
        } else {
            if (!pref.isShowed()){
                navController.navigate(R.id.onBoardPagingFragment)
            }
        }

    }
}