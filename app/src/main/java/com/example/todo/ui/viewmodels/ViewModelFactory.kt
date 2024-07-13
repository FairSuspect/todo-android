package com.example.todo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.TodoRepositoryImpl
import javax.inject.Inject


class TodoViewModelFactory @Inject constructor (private val todoRepository: TodoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodosListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodosListViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}