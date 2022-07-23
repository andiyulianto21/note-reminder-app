package com.example.notereminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notereminder.adapter.RvAdapterNote
import com.example.notereminder.databinding.ActivityMainBinding
import com.example.notereminder.fragment.NewNoteDialogFragment
import com.example.notereminder.model.Note
import com.example.notereminder.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterNote: RvAdapterNote
    private var noteList: List<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRvList()

        binding.floatingActionButton.setOnClickListener {
            val fragment = NewNoteDialogFragment()
            fragment.show(supportFragmentManager, NewNoteDialogFragment::class.java.simpleName)
        }
    }

    private fun showRvList() {
        binding.recyclerViewNote.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.noteList.observe(this) {
            noteList = it
            adapterNote = RvAdapterNote(noteList!!)
            binding.recyclerViewNote.adapter = adapterNote
        }
    }
}