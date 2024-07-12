package com.example.todo.di.modules

import com.example.todo.data.TodoDataSource
import com.example.todo.data.TodoRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Scope


@Scope
annotation class RemoteScope

@Module
interface  RemoteModule {
    @Binds
    @RemoteScope
    fun bindRemoteDataSource(remoteDataSource: TodoRemoteDataSource): TodoDataSource


}