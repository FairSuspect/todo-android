package com.example.todo.domain

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
object LocalDateTimeConverter {
    private val formatter =DateTimeFormatter.ISO_OFFSET_DATE_TIME
        private val TAG = "LocalDateTimeConverter"

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        Log.d(TAG, "Converting $value to LocalDateTime")
        return value?.let {
            LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        val formattedDate = date?.format(formatter)
        Log.d(TAG, "Converting $date to $formattedDate")
        return formattedDate
    }
}