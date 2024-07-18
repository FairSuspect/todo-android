package com.example.todo.data

import android.util.Log
import com.example.todo.di.modules.BackgroundOneThreadDispatcher
import com.example.todo.domain.Todo
import com.example.todo.domain.TodoId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
    @BackgroundOneThreadDispatcher private val dispatcher: CoroutineDispatcher,

    ) : TodoRepository {

    private val TAG = "TodoRepositoryImpl"

    override suspend fun createTodo(todo: Todo) : Unit = withContext(dispatcher) {
        // Логика создания задачи, например:
        localDataSource.createTodo(todo)
//        remoteDataSource.createTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) : Unit = withContext(dispatcher) {
        // Логика обновления задачи
        localDataSource.updateTodo(todo)
        Log.d("TodoRepositoryImpl", "updateTodo: $todo")
//        remoteDataSource.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo): Unit = withContext(dispatcher) {
        // Логика удаления задачи
        localDataSource.deleteTodo(todo)
//        remoteDataSource.deleteTodo(todo)
    }

    override suspend fun getAllTodos(): List<Todo>  = withContext(dispatcher){
//        // Логика получения всех задач, например:
         try {
            val remoteTodos = remoteDataSource.getAllTodos()
            localDataSource.updateAllTodos(remoteTodos) // Обновляем локальные данные
            remoteTodos
        } catch (e: Exception) {
            Log.e(TAG, "getAllTodos: $e")
            val localTodos =
                localDataSource.getAllTodos() // Возвращаем локальные данные в случае ошибки
            localTodos
        }
    }

    override suspend fun getTodo(id: TodoId): Todo = withContext(dispatcher){
        // Логика получения задачи по id, например:
         try {
            remoteDataSource.getTodo(id)
        } catch (e: Exception) {
            localDataSource.getTodo(id)
        }
    }


}