package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.ui.detail.DetailedTodoPage
import com.example.todo.ui.list.TodoListPage
import com.example.todo.ui.viewmodels.TodosListViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            val viewModel = hiltViewModel<TodosListViewModel>()
            TodoListPage(
                todoViewModel = viewModel,
                navController = navController,
            )
        }
        composable(
            route = "detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.StringType }),
            content = {
                val todoId =
                    listOf(navArgument("todoId") { type = NavType.StringType }).first().name
                DetailedTodoPage(
                    todoId = todoId,
                    onCloseButtonClick = { navController.popBackStack() },
                    onSaveButtonClick = { }
                )
            }
        )
    }
//    val listViewModel: TodosListViewModel = viewModel(factory = TodosListViewModel.Factory)

}