package com.example.todo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.TodoApplication
import com.example.todo.data.TodoRepository
import com.example.todo.di.ListScreenScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ListScreenScope
class TodosListViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as TodoApplication
                val todoRepository = app.todoRepository
                TodosListViewModel(
                    todoRepository = todoRepository,
                )
            }
        }
    }

}