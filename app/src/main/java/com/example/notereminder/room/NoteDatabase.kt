package com.example.notereminder.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notereminder.Converters
import com.example.notereminder.dao.NoteDao
import com.example.notereminder.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    
    abstract val noteDao: NoteDao
    
    companion object {
        private var INSTANCE: NoteDatabase? = null
        val DB_NAME = "note_db"
        fun getDatabase(context: Context): NoteDatabase {
            if(INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
    
}