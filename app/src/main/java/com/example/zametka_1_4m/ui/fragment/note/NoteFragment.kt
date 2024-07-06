package com.example.zametka_1_4m.ui.fragment.note

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zametka_1_4m.App
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.databinding.FragmentNoteBinding
import com.example.zametka_1_4m.ui.activity.MainActivity
import com.example.zametka_1_4m.ui.adapters.NoteAdapter
import com.example.zametka_1_4m.ui.adapters.NoteModel
import com.example.zametka_1_4m.ui.interfaces.OnClick
import com.example.zametka_1_4m.ui.interfaces.OnClickItem

class NoteFragment : Fragment(), OnClick, OnClickItem {
    private var noteAdapter = NoteAdapter(this, this)
    private lateinit var binding: FragmentNoteBinding
    private var flag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgMenu.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.openDrawer()
        }
        noteAdapter = NoteAdapter(this, this)
        val list = App.db?.noteDao()?.getAll()
        binding.rvNote.adapter = noteAdapter
        noteAdapter.submitList(list)
        initAdapter()
    }


    override fun onResume() {
        super.onResume()
        updateNoteList()
    }

    private fun initAdapter() = with(binding) {
        btnPlus.setOnClickListener {
            findNavController().navigate(R.id.ditailFragment)
        }
        imgShape.setOnClickListener {
            if (flag) {
                imgShape.setImageResource(R.drawable.ic_shape)
                binding.rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
                flag = false
            } else {
                imgShape.setImageResource(R.drawable.ic_menu)
                binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
                flag = true
            }
        }
    }

    private fun updateNoteList() {
        val notes = App.db?.noteDao()?.getAll()
        Log.e("ololo", "updateNoteList: $notes")
        noteAdapter.submitList(notes)
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить?")
            setPositiveButton("Да") { dialog, which ->
                App.db?.noteDao()?.deleteNote(noteModel)
                updateNoteList()
            }
            setNegativeButton("Нет") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
        builder.create()

    }

    override fun onClick(noteModel: NoteModel) {
        Log.e("ololo", "onClick: ${noteModel.id}")
        val action = NoteFragmentDirections.actionNoteFragmentToDitailFragment(noteModel.id)
        findNavController().navigate(action)
    }
}