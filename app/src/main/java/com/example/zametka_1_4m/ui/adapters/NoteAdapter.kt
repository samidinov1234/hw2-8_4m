package com.example.zametka_1_4m.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zametka_1_4m.databinding.ItemNoteBinding
import com.example.zametka_1_4m.ui.interfaces.OnClick
import com.example.zametka_1_4m.ui.interfaces.OnClickItem

class NoteAdapter(private val onLongClick: OnClickItem, private val onClick: OnClick) :
    ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(getItem(position))
            true
        }
        /*holder.itemView.setOnClickListener {
            onClick.onClick(getItem(position))
        }*/
    }


    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(noteModel: NoteModel) {
            binding.apply {
                tvTime.text = noteModel?.time
                tvText.text = noteModel?.text
                tvTitle.text = noteModel?.title
                tvDate.text = noteModel?.date
                itemView.setBackgroundColor(noteModel.color)

            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.title == newItem.title && oldItem.text == newItem.text
        }
    }
}