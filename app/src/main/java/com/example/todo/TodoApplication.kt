package com.example.todo

import android.app.Application

import com.example.todo.data.TodoRepository
import javax.inject.Inject


class TodoApplication : Application() {

    @Inject
    lateinit var todoRepository: TodoRepository

    val appComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)


    }

}