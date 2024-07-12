package com.example.todo.data

import com.example.todo.domain.Todo
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource
) : TodoRepository {

    override suspend fun createTodo(todo: Todo) {
        // Логика создания задачи, например:
        localDataSource.createTodo(todo)
//        remoteDataSource.createTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        // Логика обновления задачи
        localDataSource.updateTodo(todo)
//        remoteDataSource.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        // Логика удаления задачи
        localDataSource.deleteTodo(todo)
//        remoteDataSource.deleteTodo(todo)
    }

    override suspend fun getAllTodos(): List<Todo> {
//        // Логика получения всех задач, например:
        return try {
            val remoteTodos = remoteDataSource.getAllTodos()
            localDataSource.updateAllTodos(remoteTodos) // Обновляем локальные данные
            remoteTodos
        } catch (e: Exception) {
            val localTodos =
                localDataSource.getAllTodos() // Возвращаем локальные данные в случае ошибки
            localTodos
        }
    }

    override suspend fun getTodo(id: String): Todo {
        // Логика получения задачи по id, например:
        return try {
            remoteDataSource.getTodo(id)
        } catch (e: Exception) {
            localDataSource.getTodo(id)
        }
    }
}