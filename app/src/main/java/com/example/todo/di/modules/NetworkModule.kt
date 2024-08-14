package com.example.todo.di.modules

import android.util.Log
import com.example.todo.di.models.BaseUrlProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val TAG = "NetworkModule"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideBaseUrlProvider(): BaseUrlProvider {
        return object : BaseUrlProvider {
            override fun getBaseUrl(): String {
                val defaultUrl = "https://beta.mrdekk.ru/todobackend/"
                val baseUrl = FirebaseRemoteConfig.getInstance().getString("base_url")
                Log.d(TAG, "getBaseUrl: $baseUrl")
                return baseUrl.ifEmpty { defaultUrl }
            }
        }
    }
}