package com.example.todo.di

import android.content.Context
import com.example.todo.TodoApplication
import com.example.todo.di.modules.DataBaseModule
import com.example.todo.di.modules.DatabaseScope
import com.example.todo.di.modules.RemoteModule
import com.example.todo.di.modules.RemoteScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataBaseModule::class,
        RemoteModule::class
    ]
)
@DatabaseScope
@RemoteScope
interface AppComponent {
    fun inject(application: TodoApplication)

    fun listScreenComponent(): ListScreenComponent

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

}