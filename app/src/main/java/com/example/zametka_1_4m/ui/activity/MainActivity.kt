package com.example.zametka_1_4m.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.data.pref.Pref
import com.example.zametka_1_4m.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private val pref by lazy {
        Pref(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDrawerNav()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.drawerNavView.setupWithNavController(navController)

        if (!pref.getUserAuthed()) {
            navController.navigate(R.id.authFragment)
        } else {
            if (!pref.isShowed()) {
                navController.navigate(R.id.onBoardPagingFragment)
            } else {
                initListener()
            }
        }

    }

    private fun initListener() {
        val navigationView: NavigationView = findViewById(R.id.drawer_nav_view) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val logoutButton: Button = headerView.findViewById(R.id.btn_log_out) as Button
        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            pref?.setUserAuth(false)
            val intent = this.intent
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        val name: String? = pref?.getSavedName()
        val login: String? = pref?.getSavedLogin()
        val avatar: String? = pref?.getSavedAvatar()
        val tvLogin: TextView = headerView.findViewById(R.id.tv_user_email) as TextView
        tvLogin.text = login
        val tvName: TextView = headerView.findViewById(R.id.tv_user_name) as TextView
        tvName.text = name ?: "Enter your name"
        val ivAvatar: ImageView = headerView.findViewById(R.id.iv_user_avatar) as ImageView
        if (avatar != null) {
            Glide.with(this).load(avatar).circleCrop().into(ivAvatar);
        }
    }

    private fun initDrawerNav() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.menu_open,
            R.string.menu_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
}