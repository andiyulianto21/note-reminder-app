package com.example.notereminder.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notereminder.Converters
import com.example.notereminder.databinding.FragmentNewNoteDialogBinding
import com.example.notereminder.model.Note
import com.example.notereminder.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewNoteDialogFragment : DialogFragment() {

    private var calendar = Calendar.getInstance()
    private var year: Int = calendar.get(Calendar.YEAR)
    private var month: Int = calendar.get(Calendar.MONTH)
    private var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    private var hour: Int = calendar.get(Calendar.HOUR)
    private var minute: Int = calendar.get(Calendar.MINUTE)
    private lateinit var binding: FragmentNewNoteDialogBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewNoteDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener { dialog?.dismiss() }

        binding.btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnTimePicker.setOnClickListener {
            showTimePickerDialog()
        }

        binding.btnAddNew.setOnClickListener {
            if(binding.inputNewContent.text.toString() == "" || binding.inputNewTitle.text.toString() == ""){
                Toast.makeText(requireActivity(), "Title & Content Empty!", Toast.LENGTH_SHORT).show()
            }else {
                addNewNote()
            }
        }
    }

    private fun addNewNote() {
            noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
//            val dateTime = "${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.get(Calendar.MONTH)} ${calendar.get(Calendar.YEAR)}" +
//                    "${calendar.get(Calendar.HOUR)} ${calendar.get(Calendar.MINUTE)}"
            val dateTime = SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.time)
            val newNote = Note(
                title = binding.inputNewTitle.text.toString(),
                content = binding.inputNewContent.text.toString(),
                dateTime = dateTime
            )
            noteViewModel.insertNote(requireActivity(), newNote)
            Toast.makeText(requireActivity(), "New note has been added!", Toast.LENGTH_SHORT).show()
        dialog?.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showTimePickerDialog() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hours, minutes ->
            this.hour = view.hour
            this.minute = view.minute
            calendar.set(year, month, day, hour, minute)
            binding.btnTimePicker.text = "Time: ${SimpleDateFormat("HH:mm").format(calendar.time)}"
        }

        TimePickerDialog(
            requireActivity(),
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, years, months, days ->
            this.year = view.year
            this.month = view.month
            this.day = view.dayOfMonth
            calendar.set(year, month, day, hour, minute)
            binding.btnDatePicker.text = "Date: ${SimpleDateFormat("dd MMMM yyyy").format(calendar.time)}"
        }

        DatePickerDialog(requireActivity(), dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onResume() {
        super.onResume()
        val params: LayoutParams = dialog!!.window!!.attributes
        params.width = LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}