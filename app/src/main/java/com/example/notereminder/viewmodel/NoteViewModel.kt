package com.example.notereminder.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notereminder.dao.NoteDao
import com.example.notereminder.model.Note
import com.example.notereminder.repository.NoteRepository

class NoteViewModel(application: Application): AndroidViewModel(application) {

    var noteList: LiveData<List<Note>>
    private var noteRepository: NoteRepository = NoteRepository()

    init {
        noteList = noteRepository.getAll(application)
    }

    fun insertNote(context: Context, note: Note) {
        noteRepository.insertNote(context, note)
    }

    fun updateNote(context: Context, note: Note) {
        noteRepository.updateNote(context, note)
    }

    fun deleteNote(context: Context, note: Note) {
        noteRepository.deleteNote(context, note)
    }

}