package com.example.todo.ui.viewmodels

import com.example.todo.domain.Todo

sealed class TodoListUiState {
    data object Loading : TodoListUiState()
    data class Loaded(val todos: List<Todo>) : TodoListUiState()
    data class Error(val message: String) : TodoListUiState()
}