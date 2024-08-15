package com.example.todo.data

import LocalDateTimeAdapter
import com.example.todo.di.models.BaseUrlProvider
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Inject


class RetrofitClient @Inject constructor(
    private val baseUrlProvider: BaseUrlProvider
) {
    val todoApi: TodoApi by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
        val authInterceptor = AuthInterceptor("Wrosdon")
        Retrofit.Builder()
            .baseUrl(baseUrlProvider.getBaseUrl()) // Use injected provider
            .addConverterFactory(GsonConverterFactory.create(gson))

            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .build()
            .create(TodoApi::class.java)
    }
}