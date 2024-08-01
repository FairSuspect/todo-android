package com.example.todo.ui.viewmodels.detail

import com.example.todo.domain.Todo

sealed class TodoDetailsUiState {
    data object Loading : TodoDetailsUiState()
    data class Error(val message: String) : TodoDetailsUiState()
    data class Loaded(val todo: Todo) : TodoDetailsUiState()
}