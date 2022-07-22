package com.example.notereminder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notereminder.model.Note
import com.example.notereminder.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class NoteRepository {

    companion object {
        private var noteDatabase : NoteDatabase? = null
        private var noteList: LiveData<List<Note>>? = null
        
        fun initializeDb(context: Context):NoteDatabase {
            noteDatabase = NoteDatabase.getDatabase(context)
            return noteDatabase as NoteDatabase
        }
        
        fun getAll(context: Context): LiveData<List<Note>> {
            
            noteList = initializeDb(context).noteDao.getAll()
            return noteList as LiveData<List<Note>>
        }
        
        fun insertNote(context: Context, note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                initializeDb(context).noteDao.insertNote(note)
            }
        }
    
        fun updateNote(context: Context, note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                initializeDb(context).noteDao.updateNote(note)
            }
        }
    
        fun deleteNote(context: Context, note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                initializeDb(context).noteDao.deleteNote(note)
            }
        }
    }

}