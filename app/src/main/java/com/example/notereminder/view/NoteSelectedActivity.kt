package com.example.notereminder.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notereminder.*
import com.example.notereminder.databinding.ActivityNoteSelectedBinding
import com.example.notereminder.model.Note
import com.example.notereminder.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteSelectedActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNoteSelectedBinding
    private val calendar = Calendar.getInstance()
    private val oldDate = Calendar.getInstance()
    private var isDateReset = false
    private var isTimeReset = false
    private var id: Int? = null
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var sdfDate: String
    private lateinit var sdfTime: String
    private lateinit var date: String
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private var hour = calendar.get(Calendar.HOUR)
    private var minute = calendar.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteSelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNoteSelected()

        binding.btnEdit.setOnClickListener(this)
        binding.btnEditTimePicker.setOnClickListener(this)
        binding.btnEditDatePicker.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setNoteSelected() {
        this.id = intent.getIntExtra(EXTRA_ID, 0)
        this.title = intent.getStringExtra(EXTRA_TITLE).toString()
        this.content = intent.getStringExtra(EXTRA_CONTENT).toString()
        date = intent.getStringExtra(EXTRA_DATETIME).toString()
        Log.d("debug", "setNoteSelected: $date")

        binding.inputEditTitle.setText(title)
        binding.inputEditContent.setText(content)

        val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm").parse(date)
        if (sdf != null) {
            calendar.time = sdf
        }
        sdfDate = SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
        sdfTime = SimpleDateFormat("HH:mm").format(calendar.time)

        binding.btnEditDatePicker.text = sdfDate
        binding.btnEditTimePicker.text = sdfTime
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_edit ->
                if(binding.inputEditTitle.text.toString() == "" || binding.inputEditContent.text.toString() == ""){
                    Toast.makeText(this, "Title or Content Empty", Toast.LENGTH_SHORT).show()
                }else {
                    editData()
                }
            R.id.btn_edit_date_picker -> editDatePicker()
            R.id.btn_edit_time_picker -> editTimePicker()
        }
    }

    private fun editDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, _, _, _ ->
            this.year = view.year
            this.month = view.month
            this.day = view.dayOfMonth
            calendar.set(year, month, day, hour, minute)
            binding.btnEditDatePicker.text = SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
        }
        DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun editTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, _, _ ->
            this.hour = view.hour
            this.minute = view.minute
            calendar.set(year, month, day, hour, minute)
            binding.btnEditTimePicker.text = SimpleDateFormat("HH:mm").format(calendar.time)
        }

        TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

    private fun editData() {
        val editNote = Note(
            id = id!!,
            title = binding.inputEditTitle.text.toString(),
            content = binding.inputEditContent.text.toString(),
            dateTime = SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.time)
        )

        val noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.updateNote(this@NoteSelectedActivity, editNote)
        finish()
    }

    private fun resetAllData() {
        isDateReset = true
        isTimeReset = true
        binding.inputEditTitle.setText(title)
        binding.inputEditContent.setText(content)
        binding.btnEditTimePicker.text = sdfTime
        binding.btnEditDatePicker.text = sdfDate
        val sdfOldDate = SimpleDateFormat("dd MMMM yyyy HH:mm").parse(date)
        oldDate.time = sdfOldDate!!
    }
}