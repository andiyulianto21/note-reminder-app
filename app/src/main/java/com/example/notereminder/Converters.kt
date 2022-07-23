package com.example.notereminder

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun calendarToDate(calendar: Calendar): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val locale = Locale("id", "IN")
        val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm")
        return sdf.format(calendar.time).toString()
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun dateToCalendar(date: String): Calendar {
        val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm").parse(date)
        val calendar = Calendar.getInstance()
        if (sdf != null) {
            calendar.set(sdf.year, sdf.month, sdf.day, sdf.hours, sdf.minutes)
        }
        return calendar
    }
}