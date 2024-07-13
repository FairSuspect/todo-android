package com.example.todo.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.domain.Todo
import com.example.todo.ui.theme.TodoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoTile(
    modifier: Modifier = Modifier,
    todo: Todo,
    onDoneChanged: (done: Boolean) -> Unit,
    onDelete: () -> Unit = {},
) {
    val iconColor = MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable { onDoneChanged(!todo.done) }
            .combinedClickable(onLongClick = onDelete, onClick = { onDoneChanged(!todo.done) }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = todo.done, onCheckedChange = onDoneChanged)
        Text(todo.text)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
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