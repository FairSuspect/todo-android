package com.example.todo.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.TodoTheme

@Composable
fun AddTodoTile(onDone: (text: String) -> Unit = {}) {
    val iconColor = MaterialTheme.colorScheme.onSurface
    var text by remember { mutableStateOf("") }
    val paddingModifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp)
    BasicTextField(
        modifier = paddingModifier,
        value = text,
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardActions = KeyboardActions(
            onDone = { onDone(text.trim()) }
        ),
        decorationBox = { innerTextField ->
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                        tint = iconColor,
                    )
                },
                headlineContent = {
                    Box(Modifier.fillMaxSize().padding(start = 12.dp),
                    ) {
                        innerTextField()
                        if (text.isBlank())
                            Text(
                                stringResource(R.string.newTask),
                            )
                    }
                }
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