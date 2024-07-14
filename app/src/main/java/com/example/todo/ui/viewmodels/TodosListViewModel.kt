package com.example.todo.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepositoryImpl
import com.example.todo.domain.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

val TAG = "TodosListViewModel"

@HiltViewModel
class TodosListViewModel @Inject constructor(private val todoRepository: TodoRepositoryImpl) :
    ViewModel() {
    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // ... логика вашего ViewModel, например:
    fun getTodos() {
        viewModelScope.launch {
            _uiState.value = TodoListUiState.Loading
            try {
                val todos = todoRepository.getAllTodos()
                _uiState.value = TodoListUiState.Loaded(todos)
            } catch (e: Exception) {
                _uiState.value = TodoListUiState.Error("Failed to load todos: ${e.message}")
            }
        }

        // Используйте todoRepository для получения списка задач
    }

    init {
        getTodos() // Загрузка данных при создании ViewModel
    }

    fun createRandomTodo() {
        val randomTodoId = (1..100000).random()
        val randomTodo =
            Todo(id = randomTodoId.toString(), text = "Random Todo: $randomTodoId", done = false)

        createTodo(randomTodo)
    }

    private fun createTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                todoRepository.createTodo(todo)
                val todos = (_uiState.value as TodoListUiState.Loaded).todos
                _uiState.value = TodoListUiState.Loaded(todos + todo)
            } catch (e: Exception) {
                val message = "Не удалось создать задачу: ${e.message}"
                Log.e(TAG, message)

            }
        }
    }

    fun createTodoWithText(text: String) {
        // Создаётся задача с id сгенерированным uuid, text и done false
        val todo = Todo(id = generateId(), text = text, done = false)
        createTodo(todo)

    }
    private fun generateId(): String {
        return  UUID.randomUUID().toString()
    }

    private fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                todoRepository.updateTodo(todo)
                val todos = (_uiState.value as TodoListUiState.Loaded).todos
                val updatedTodos = todos.map {
                    if (it.id == todo.id) todo else it
                }
                _uiState.update {
                    (it as TodoListUiState.Loaded).copy(todos = updatedTodos)
                }
            } catch (e: Exception) {
                val message = "Не удалось обновить задачу: ${e.message}"
                Log.e(TAG, message)
            }
        }
    }

    fun onChecked(todo: Todo, checked: Boolean) {
        val newTodo = todo.copy(done = checked, changeAt = LocalDateTime.now(ZoneId.of("UTC")))
        updateTodo(newTodo)
    }

    fun deleteTodo(todo: Todo) {
        Log.d(TAG, "deleteTodo: $todo")
        viewModelScope.launch {
            try {
                todoRepository.deleteTodo(todo)
                _uiState.update { state ->
                    var todos = (state as TodoListUiState.Loaded).todos
                    todos = todos.filter { it.id != todo.id }
                    TodoListUiState.Loaded(todos)
                }

            } catch (e: Exception) {
                val message = "Не удалось удалить задачу: ${e.message}"
                Log.e(TAG, message)
            }
        }
    }

    fun onVisibilityChanged(visible: Boolean) {
        if (_uiState.value !is TodoListUiState.Loaded)
            return
        val loadedState = _uiState.value as TodoListUiState.Loaded

        _uiState.value =
            loadedState.copy(filterState = if (visible) TodoListUiState.FilterState.ALL else TodoListUiState.FilterState.NOT_COMPLETED)
    }
}