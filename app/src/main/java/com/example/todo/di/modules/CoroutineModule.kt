package com.example.todo.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Qualifier

@Qualifier
annotation class BackgroundDispatcher

@Qualifier
annotation class BackgroundOneThreadDispatcher

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineModule {
    companion object {
        @Provides
        @BackgroundDispatcher
        fun backgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @OptIn(ExperimentalCoroutinesApi::class)
        @Provides
        @BackgroundOneThreadDispatcher
        fun backgroundOneThreadDispatcher(@BackgroundDispatcher backgroundDispatcher: CoroutineDispatcher): CoroutineDispatcher =
            backgroundDispatcher.limitedParallelism(1)
    }
}