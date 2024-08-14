package com.example.todo.ui.viewmodels

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepositoryImpl
import com.example.todo.domain.Todo
import com.example.todo.notifications.NotificationReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import java.util.UUID
import javax.inject.Inject


const val TAG = "TodosListViewModel"

@HiltViewModel
class TodosListViewModel @Inject constructor(
    private val todoRepository: TodoRepositoryImpl,
    private val notificationManager: NotificationManager,
    @ApplicationContext
    private val applicationContext: Context

) :
    ViewModel() {
    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // ... логика вашего ViewModel, например:
     fun getTodos() {
        viewModelScope.launch {
            _uiState.value = TodoListUiState.Loading
            try {
                val todos = todoRepository.getAllTodos()
                _uiState.value = TodoListUiState.Loaded(todos)
            } catch (e: Exception) {
                _uiState.value = TodoListUiState.Error("Failed to load todos: ${e.message}")
            }
        }

        // Используйте todoRepository для получения списка задач
    }

    init {
        getTodos() // Загрузка данных при создании ViewModel
    }

    fun createRandomTodo() {
        val randomTodoId = (1..100000).random()
        val deadline = LocalDateTime.now().plusMinutes(1)
        val randomTodo =
            Todo(
                id = randomTodoId.toString(),
                text = "Random Todo: $randomTodoId",
                done = false,
                deadline = deadline
            )

        createTodo(randomTodo)
    }

    private fun createTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                todoRepository.createTodo(todo)
                val todos = (_uiState.value as TodoListUiState.Loaded).todos
                _uiState.value = TodoListUiState.Loaded(todos + todo)
                createNotificationChannel()
                if (todo.deadline != null) {
                    val calendar = Calendar.getInstance()
                    val notificationLocalDateTime = todo.deadline.minusSeconds(30)
                    val dateTime: Date = Date.from(
                        notificationLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
                    )
                    calendar.time = dateTime

                    scheduleNotification(calendar, todo)
                }
            } catch (e: Exception) {
                val message = "Не удалось создать задачу: ${e.message}"
                Log.e(TAG, message)

            }
        }
    }

    fun createTodoWithText(text: String) {
        // Создаётся задача с id сгенерированным uuid, text и done false
        val todo = Todo(id = generateId(), text = text, done = false)
        createTodo(todo)

    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }

    /// Этот метод создаёт канал уведомлений, если его ещё нет.
    /// Этот канал уведомлений используется только для напоминаний о дедлайнах по задачам
    private fun createNotificationChannel() {
        val id = "reminder"
        val name = "Напоминания о дедлайнах"
        val des = "Канал для напоминаний о дедлайнах"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)
        channel.description = des
        notificationManager.createNotificationChannel(channel)
        Log.d(TAG, "createNotificationChannel: $channel")
    }


    private fun scheduleNotification(calendar: Calendar, todo: Todo) {
        val notificationId = todo.id.hashCode()
        val intent = Intent(applicationContext, NotificationReceiver::class.java).apply {
            putExtra("TODO_ID", todo.id) // Pass the todoID to the receiver
            putExtra("titleExtra", todo.text)
            putExtra("textExtra", "Осталось совсем немного времени")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        logAtExpectedTime(calendar)


    }

    private fun logAtExpectedTime(calendar: Calendar) {
        val formattedDate = calendar.time.toString()
        Log.d(TAG, "Scheduled notification. Expected at $formattedDate")
        // Для логирования запускаем таймер на 30 секунд
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Log.d(TAG, "Push notification expected to appear now ($formattedDate)")
            }
        }, 30000)
    }


    private fun updateTodo(todo: Todo) {
    viewModelScope.launch {
        try {
            todoRepository.updateTodo(todo)
            val todos = (_uiState.value as TodoListUiState.Loaded).todos
            val updatedTodos = todos.map {
                if (it.id == todo.id) todo else it
            }
            _uiState.update {
                (it as TodoListUiState.Loaded).copy(todos = updatedTodos)
            }
        } catch (e: Exception) {
            val message = "Не удалось обновить задачу: ${e.message}"
            Log.e(TAG, message)
        }
    }
}

fun onChecked(todo: Todo, checked: Boolean) {
    val newTodo = todo.copy(done = checked, changeAt = LocalDateTime.now(ZoneId.of("UTC")))
    updateTodo(newTodo)
}

fun deleteTodo(todo: Todo) {
    Log.d(TAG, "deleteTodo: $todo")
    viewModelScope.launch {
        try {
            todoRepository.deleteTodo(todo)
            _uiState.update { state ->
                var todos = (state as TodoListUiState.Loaded).todos
                todos = todos.filter { it.id != todo.id }
                TodoListUiState.Loaded(todos)
            }

        } catch (e: Exception) {
            val message = "Не удалось удалить задачу: ${e.message}"
            Log.e(TAG, message)
        }
    }
}

fun onVisibilityChanged(visible: Boolean) {
    if (_uiState.value !is TodoListUiState.Loaded)
        return
    val loadedState = _uiState.value as TodoListUiState.Loaded

    _uiState.value =
        loadedState.copy(filterState = if (visible) TodoListUiState.FilterState.ALL else TodoListUiState.FilterState.NOT_COMPLETED)
}
}