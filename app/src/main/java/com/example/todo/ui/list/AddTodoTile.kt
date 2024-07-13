package com.example.todo.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.TodoTheme

@Composable
fun AddTodoTile(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val iconColor = MaterialTheme.colorScheme.onSurface
    ListItem(
        modifier = modifier.clickable { onClick() }.padding(start = 12.dp),
        headlineContent = {
            Text(
                stringResource(R.string.createTask),
            )
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = iconColor,
            )
        }
    )

}


@Preview
@Composable
private fun AddTodoTilePreview() {


    TodoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

                AddTodoTile()

            }
        }
    }
}