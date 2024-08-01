package com.example.todo.ui.viewmodels.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepositoryImpl
import com.example.todo.domain.Todo
import com.example.todo.domain.TodoId
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TodoDetailViewModel @AssistedInject constructor(
    @Assisted todoId: TodoId?,
    private val todoRepository: TodoRepositoryImpl,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailsUiState>(TodoDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTodoById(todoId)
    }
    private fun getTodoById(todoId: TodoId?) {

        if (todoId == null) {
            _uiState.value = TodoDetailsUiState.Loaded(Todo.empty)
            return
        }
        viewModelScope.launch {
            _uiState.value = TodoDetailsUiState.Loading
            try {
                val todo = todoRepository.getTodo(todoId)
                _uiState.value = TodoDetailsUiState.Loaded(todo)
            } catch (e: Exception) {
                _uiState.value = TodoDetailsUiState.Error("Failed to load todo: ${e.message}")
            }
        }
    }

    fun onRetryButtonClick(todoId: TodoId?){
        getTodoById(todoId)
    }

    @AssistedFactory
    interface Factory {
        fun create(itemId: TodoId?): TodoDetailViewModel
    }
}