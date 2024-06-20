package com.example.todo.data

import com.example.todo.domain.Todo
import retrofit2.http.GET


interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    // Другие endpoints, если необходимо
}