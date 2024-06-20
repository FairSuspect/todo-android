package com.example.todo.data

import com.example.todo.domain.Todo
import com.example.todo.data.TodoRepository


class TodoRemoteDataSource : TodoRepository {
    override suspend fun getAllTodos(): List<Todo> {
        return RetrofitClient.todoApi.getTodos()
    }

    // Реализация других методов TodoRepository, если необходимо
}