package com.example.zametka_1_4m.ui.interfaces

import com.example.zametka_1_4m.ui.adapters.NoteModel

interface OnClickItem {
    fun onLongClick(noteModel: NoteModel)
}