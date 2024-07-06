package com.example.zametka_1_4m.ui.fragment.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zametka_1_4m.App
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.databinding.FragmentDitailBinding
import com.example.zametka_1_4m.ui.adapters.NoteModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DitailFragment : Fragment() {
    private lateinit var binding: FragmentDitailBinding
    private var noteId: Int = -1
    var color: Int = 0
    var timeText = ""
    var dateText = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDitailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = when (checkedId) {
                binding.radio1.id -> binding.radio1
                binding.radio2.id -> binding.radio2
                binding.radio3.id -> binding.radio3
                else -> binding.radio1
            }
            radioButton.let {
                when (it.tag) {
                    "red" -> color = R.color.red
                    "black" -> color = R.color.black2
                    "white" -> color = R.color.white2
                }
            }
        }

        val currentDate = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        dateText = dateFormat.format(currentDate)
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeText = timeFormat.format(currentDate)
        binding.tvTime.text = timeText
        binding.tvDate.text = dateText

        setupTextChangedListener()
        checkButtonVisibility()
        initListener()
        //changeData()
        //getArgument()
    }


    private fun setupTextChangedListener() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkButtonVisibility()
            }
        })

        binding.etText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkButtonVisibility()
            }
        })
    }

    private fun checkButtonVisibility() {
        val titleText = binding.etTitle.text.toString().trim()
        val text = binding.etText.text.toString().trim()

        binding.tvSend.visibility = if (titleText.isNotEmpty() && text.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun initListener() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
            Log.e("ololo", "initListener: $noteId", )
        }
        if (noteId!=-1){
            val note: NoteModel = App.db.noteDao().getNoteById(noteId)
            binding.etText.setText(note.text)
            binding.etTitle.setText(note.title)
        }
        binding.tvSend.setOnClickListener {
            if (noteId != -1) {
                val noteModel = NoteModel(
                    id = noteId,
                    title = binding.etTitle.text.toString(),
                    text = binding.etText.text.toString(),
                    color = color,
                    time = timeText,
                    date = dateText
                )
                App.db?.noteDao()?.updateNote(noteModel)
            }else{
                val noteModel = NoteModel(
                    title = binding.etTitle.text.toString(),
                    text = binding.etText.text.toString(),
                    color = color,
                    time = timeText,
                    date = dateText
                )
                App.db?.noteDao()?.insertNote(noteModel)
            }
            findNavController().navigate(
                R.id.noteFragment
            )
        }
    }

}