package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.di.AppComponent
import com.example.todo.ui.list.TodoListPage
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.viewmodels.TodosListViewModel
import com.example.todo.ui.viewmodels.TodoViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    //    fun Fragment.getAppComponent(): AppComponent =
//        (requireContext() as TodoApplication).appComponent
    @Inject
    lateinit var viewModelFactory: TodoViewModelFactory
    private val viewModel: TodosListViewModel by viewModels { viewModelFactory }



    private fun getAppComponent(): AppComponent {
        val todoApplication = application as TodoApplication

        return todoApplication.appComponent
    }


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
                TodoListPage(modifier = Modifier.fillMaxSize())
            }
        }
    }
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