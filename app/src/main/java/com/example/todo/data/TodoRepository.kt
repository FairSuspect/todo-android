package com.example.todo.data

import com.example.todo.domain.Todo

interface  TodoRepository {
    suspend fun createTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun  getAllTodos(): List<Todo>

    suspend fun getTodo(id: String): Todo
}