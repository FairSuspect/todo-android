package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todo.ui.viewmodels.TodosListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val listViewModel: TodosListViewModel = viewModel(factory = TodosListViewModel.Factory)

}