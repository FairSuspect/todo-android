package com.example.todo.data

import android.util.Log
import com.example.todo.domain.Todo
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class TodoRemoteDataSource @Inject constructor(
    private val retrofitClient: RetrofitClient
) : TodoDataSource {
    override suspend fun createTodo(todo: Todo): Todo {
        Log.d("TodoRemoteDataSource", "Creating todo: $todo")
       return retrofitClient.todoApi.createTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) : Todo{
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todo: Todo) {
        return retrofitClient.todoApi.deleteTodo(todo.id)
    }

    override suspend fun getAllTodos(): List<Todo> {
        try {

        return retrofitClient.todoApi.getTodos()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.e("TodoRemoteDataSource", "Error getting todos", e)
            throw e
        }
    }

    override suspend fun getTodo(id: String): Todo {
        TODO("Not yet implemented")
    }

    override suspend fun updateAllTodos(remoteTodos: List<Todo>) {
        TODO("Not yet implemented")
    }

}