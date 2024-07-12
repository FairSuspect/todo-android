package com.example.todo.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao  {
    @Query("SELECT * FROM todo")
    fun getAllTodos() : List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: TodoId) : Todo

    @Insert
    fun createTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

}