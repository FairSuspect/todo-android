package com.example.todo.data


import com.example.todo.domain.Todo
import com.example.todo.domain.TodoId
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @POST("todos")
    suspend fun createTodo(@Body todo: Todo): Todo

    @DELETE("todos/{todoId}")
    suspend fun deleteTodo(@Path("todoId") todoId: TodoId)

    @PUT("todos/{todoId}")
    suspend fun updateTodo(todo: Todo): Todo

    @GET("todos/{todoId}")
    suspend fun getTodo(todoId: TodoId): Todo

}