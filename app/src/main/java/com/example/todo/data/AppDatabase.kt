package com.example.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.domain.LocalDateTimeConverter
import com.example.todo.domain.Todo
import com.example.todo.domain.TodoDao

@Database(entities = [Todo::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}