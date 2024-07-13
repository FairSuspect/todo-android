package com.example.todo.data

import com.example.todo.domain.Todo
import javax.inject.Inject

class TodoRemoteDataSource @Inject constructor() : TodoDataSource {
    override suspend fun createTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTodos(): List<Todo> {
        return RetrofitClient.todoApi.getTodos()
    }

    override suspend fun getTodo(id: String): Todo {
        TODO("Not yet implemented")
    }

    override suspend fun updateAllTodos(remoteTodos: List<Todo>) {
        TODO("Not yet implemented")
    }

}