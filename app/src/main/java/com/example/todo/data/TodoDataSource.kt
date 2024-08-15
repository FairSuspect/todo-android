package com.example.todo.data

import com.example.todo.domain.Todo
import com.example.todo.domain.TodoId

interface TodoDataSource {
    suspend fun createTodo(todo: Todo): Todo

    suspend fun updateTodo(todo: Todo): Todo

    suspend fun deleteTodo(todo: Todo)

    suspend fun getAllTodos(): List<Todo>

    suspend fun getTodo(id: TodoId): Todo

    suspend fun updateAllTodos(remoteTodos: List<Todo>)
}