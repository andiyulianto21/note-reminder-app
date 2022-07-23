package com.example.notereminder

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notereminder.adapter.RvAdapterNote
import com.example.notereminder.databinding.ActivityMainBinding
import com.example.notereminder.fragment.NewNoteDialogFragment
import com.example.notereminder.model.Note
import com.example.notereminder.view.NoteSelectedActivity
import com.example.notereminder.viewmodel.NoteViewModel

const val EXTRA_ID = "extra_id"
const val EXTRA_TITLE = "extra_title"
const val EXTRA_CONTENT = "extra_content"
const val EXTRA_DATETIME = "extra_datetime"

class MainActivity : AppCompatActivity(), RvAdapterNote.OnNoteClickedListener {

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
            adapterNote = RvAdapterNote(it, this)
            binding.recyclerViewNote.adapter = adapterNote

            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Delete")
                        .setMessage("Title : ${noteList?.get(viewHolder.adapterPosition)?.title}\n" +
                                "Date & Time : ${noteList?.get(viewHolder.adapterPosition)?.dateTime}\n" +
                                "\nAre you sure want to delete permanently this note?")
                        .setPositiveButton("OK", object : DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface, p1: Int) {
                                noteViewModel.deleteNote(
                                    this@MainActivity,
                                    noteList!![viewHolder.adapterPosition])
                                dialog.dismiss()
                            }
                        })
                        .setNegativeButton("Cancel", object : DialogInterface.OnClickListener{
                            @SuppressLint("NotifyDataSetChanged")
                            override fun onClick(dialog: DialogInterface, p1: Int) {
                                adapterNote.notifyDataSetChanged()
                                dialog.cancel()
                            }
                        }).show()
                }

            }).attachToRecyclerView(binding.recyclerViewNote)
        }
    }


    override fun onItemClicked(position: Int) {
        val intent = Intent(this, NoteSelectedActivity::class.java)
        intent.putExtra(EXTRA_ID, noteList?.get(position)?.id)
        intent.putExtra(EXTRA_TITLE, noteList?.get(position)?.title)
        intent.putExtra(EXTRA_CONTENT, noteList?.get(position)?.content)
        intent.putExtra(EXTRA_DATETIME, noteList?.get(position)?.dateTime)
        startActivity(intent)
    }
}