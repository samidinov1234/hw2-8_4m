package com.example.zametka_1_4m.ui.adapters

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String?=null,
    val text: String?=null,
    val time: String?=null,
    val date: String?=null,
    val color: Int = Color.BLACK

)