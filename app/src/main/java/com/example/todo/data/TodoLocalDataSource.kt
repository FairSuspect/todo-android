package com.example.todo.data

import com.example.todo.domain.Todo
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(private val db: AppDatabase) : TodoDataSource {


    override suspend fun createTodo(todo: Todo) :Todo {
        return db.todoDao().createTodo(todo).let { todo }
    }

    override suspend fun updateTodo(todo: Todo) : Todo {
        return db.todoDao().updateTodo(todo).let { todo }
    }

    override suspend fun deleteTodo(todo: Todo) {
        return db.todoDao().deleteTodo(todo)
    }

    override suspend fun getAllTodos(): List<Todo> {
        return  db.todoDao().getAllTodos()
    }

    override suspend fun getTodo(id: String): Todo {
        return  db.todoDao().getTodo(id)
    }

    override suspend fun updateAllTodos(remoteTodos: List<Todo>) {

        return db.todoDao().updateAllTodos(remoteTodos)
    }

}