package com.example.todo.di

import com.example.todo.ui.viewmodels.TodosListViewModel
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ListScreenScope

@Subcomponent
@ListScreenScope
interface ListScreenComponent {
    fun viewModel(): TodosListViewModel
}