package com.example.todo.ui.viewmodels

import com.example.todo.domain.Todo

sealed class TodoListUiState {
    data object Loading : TodoListUiState()
    data class Loaded(
        val todos: List<Todo>,
        val filterState: FilterState = FilterState.ALL,
    ) :
        TodoListUiState()

    data class Error(val message: String) : TodoListUiState()


    enum class FilterState(val filter: (Todo) -> Boolean) {
        ALL({ true }),
        NOT_COMPLETED({ !it.done })
    }
}