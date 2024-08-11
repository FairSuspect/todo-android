package com.example.todo.data.di.modules

import android.content.Context
import androidx.room.Room
import com.example.todo.data.AppDatabase
import com.example.todo.data.TodoLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton
@Scope
annotation class DatabaseScope

@Module
class DataBaseModule {
    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "todo_database"
            ).build()
        }

        @Provides
        @Singleton
        fun provideTodoLocalDataSource(db: AppDatabase): TodoLocalDataSource {
            return TodoLocalDataSource(db)
        }
    }
}