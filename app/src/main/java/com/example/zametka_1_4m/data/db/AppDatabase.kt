package com.example.zametka_1_4m.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zametka_1_4m.ui.adapters.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}