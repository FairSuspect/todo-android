package com.example.todo.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.domain.Todo
import com.example.todo.ui.theme.TodoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTile(
    modifier: Modifier = Modifier,
    todo: Todo,
    onDoneChanged: (done: Boolean) -> Unit,
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val iconColor = MaterialTheme.colorScheme.onSurface

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onDelete()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onDoneChanged(true)
                }
                SwipeToDismissBoxValue.Settled -> {}
            }
            // Изменения делаются после достижения threshold, если элемент остаётся,
            // то состояние свайпа нужно сбросить
            return@rememberSwipeToDismissBoxState false
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { TodoDismissBackground(dismissState = dismissState) },
        content = {
            ListItem(
                headlineContent = {
                Text(
                    todo.text, style = if (!todo.done) LocalTextStyle.current
                    else LocalTextStyle.current.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
                leadingContent = { Checkbox(checked = todo.done, onCheckedChange = onDoneChanged) },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                modifier = modifier.clickable { onClick() }

            )
        }
    )


}


@Preview
@Composable
private fun TodoTilePreview() {
    val todos = listOf(
        Todo(id = "123", text = "Купить пива", done = false),
        Todo(id = "1234", text = "Купить чипсов", done = true),
        Todo(id = "12345", text = "Оплатить интернет", done = true),
        Todo(id = "123456", text = "Сделать РВП", done = false),
    )

    TodoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                todos.forEach { todo ->
                    TodoTile(todo = todo, onDoneChanged = {})
                }
            }
        }
    }
}