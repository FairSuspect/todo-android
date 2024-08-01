package com.example.todo.ui.detail

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.R
import com.example.todo.domain.Importance
import com.example.todo.domain.Todo
import com.example.todo.domain.TodoId
import com.example.todo.ui.list.Loading
import com.example.todo.ui.viewmodels.detail.TodoDetailViewModel
import com.example.todo.ui.viewmodels.detail.TodoDetailsUiState
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedTodoPage(
    modifier: Modifier = Modifier,
    todoId: TodoId,
    onCloseButtonClick: () -> Unit,
    onSaveButtonClick: (Todo) -> Unit,
    todoViewModel: TodoDetailViewModel = viewModel(),

    ) {
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = onCloseButtonClick) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            },
            actions = {
                TextButton(onCloseButtonClick) {
                    Text(stringResource(R.string.save))
                }
            }

        )
    }) { innerPadding ->
        val uiState by todoViewModel.uiState.collectAsState()
        when (uiState) {
            is TodoDetailsUiState.Loaded -> {
                val padding = 16.dp
                val todo = (uiState as TodoDetailsUiState.Loaded).todo
                TodoDetails(
                    modifier
                        .padding(innerPadding)
                        .padding(padding),
                    todo = todo
                )
            }

            is TodoDetailsUiState.Loading -> {
                Loading()
            }

            is TodoDetailsUiState.Error -> {
                Column {
                    TextButton(onClick = { todoViewModel.onRetryButtonClick(todoId) }) {
                        Text(stringResource(R.string.tryAgain))
                    }
                    
                    Text(text = (uiState as TodoDetailsUiState.Error).message)
                }
            }

        }
    }
}

@Composable
private fun TodoDetails(
    modifier: Modifier,
    todo: Todo
) {
    var expanded by remember { mutableStateOf(true) }
    var todoText by remember { mutableStateOf(todo.text) }
    var importance by remember { mutableStateOf(todo.importance) }
    val context = LocalContext.current
    Column(modifier = modifier) {
        TextField(
            value = todoText,
            minLines = 3,
            maxLines = 5,
            onValueChange = {
                todoText = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box {
            TextField(
                label = { Text("Важность") },
                value = importance.getDisplayName(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                content = {
                    Importance.entries.map {
                        DropdownMenuItem(
                            text = { Text(it.getDisplayName()) },
                            onClick = {
                                expanded = false
                                importance = it
                                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                            }
                        )
                        HorizontalDivider()

                    }
                },
            )
        }
    }
}


@Preview
@Composable
private fun TodoDetailsPagePreview() {
    val exampleTodo = Todo(
        id = "ExampleId",
        done = false,
        text = "Example text",
        importance = Importance.BASIC,
        deadline = LocalDateTime.now().plusDays(1)
    )
    DetailedTodoPage(
        todoId = exampleTodo.id,
        onCloseButtonClick = {},
        onSaveButtonClick = {}
    )
}