package com.example.todo.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.ui.viewmodels.TodoListUiState
import com.example.todo.ui.viewmodels.TodosListViewModel

@Composable
fun TodoListPage(
    modifier: Modifier = Modifier,
    todoViewModel: TodosListViewModel = viewModel(),
) {
    val uiState by todoViewModel.uiState.collectAsState()
    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = {
            AddTodoFAB(onClick = {
                todoViewModel.createRandomTodo()
            })
        },
    ) { innerPadding ->

        when (uiState) {
            is TodoListUiState.Loading -> {
                // Показать индикатор загрузки
                Loading()
            }

            is TodoListUiState.Loaded -> {
                val todos = (uiState as TodoListUiState.Loaded).todos
                // Показать список задач
                LazyColumn(
                    modifier = modifier.padding(
                        innerPadding
                    )
                ) {
                    items(todos.size) { index ->
                        val todo = todos[index]
                        TodoTile(todo = todo, onDoneChanged = {
                            todoViewModel.onChecked(todo, it)
                        },
                            onDelete = {
                                todoViewModel.deleteTodo(todo)
                            }
                        )
                    }
                    item {
                        AddTodoTile()
                    }
                }
            }

            is TodoListUiState.Error -> {
                // Показать сообщение об ошибке
                val message = (uiState as TodoListUiState.Error).message
                ///
                Column {
                    Icon(
                        imageVector = Icons.Outlined.Error,
                        contentDescription = null,
                    )
                    Text(text = message)

                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    doneTasks: Int = 0,
    visible: Boolean = false,
    onVisibleChanged: (Boolean) -> Unit = {},
) {
//    val iconButton =
    TopAppBar(title = {
        Column {
            Text("Мои дела")
            Text("Выполнено - $doneTasks", style = MaterialTheme.typography.bodySmall)
        }
    }, actions = {
        if (visible) {
            IconButton(onClick = { onVisibleChanged(false) }) {
                Icon(
                    Icons.Filled.Visibility, null
                )
            }
        } else IconButton(onClick = { onVisibleChanged(true) }) {
            Icon(
                Icons.Filled.VisibilityOff, null
            )
        }
    }, modifier = modifier
    )
}


@Preview
@Composable
private fun TodoListPagePreview() {
    TodoListPage()
}

