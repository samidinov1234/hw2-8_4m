package com.example.zametka_1_4m

import android.app.Application
import androidx.room.Room
import com.example.zametka_1_4m.data.db.AppDatabase

class App : Application() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "name")
            .allowMainThreadQueries().build()
    }


}
