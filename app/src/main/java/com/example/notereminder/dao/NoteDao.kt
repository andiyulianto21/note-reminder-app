package com.example.notereminder.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notereminder.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>
    
    @Insert
    fun insertNote(note: Note)
    
    @Update
    fun updateNote(note: Note)
    
    @Delete
    fun deleteNote(note: Note)
}