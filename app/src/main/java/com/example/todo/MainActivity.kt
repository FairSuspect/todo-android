package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.navigation.NavGraph
import com.example.todo.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    fun Fragment.getAppComponent(): AppComponent =
//        (requireContext() as TodoApplication).appComponent






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "todo-database",
//        ).build()
//            val todoDao = db.todoDao()
//        val todos = todoDao.getAllTodos()


        setContent {
            TodoTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavGraph(
        navController = navController,
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoTheme {
        Greeting("Android")
    }
}