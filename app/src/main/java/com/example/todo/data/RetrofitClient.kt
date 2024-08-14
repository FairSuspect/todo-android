package com.example.todo.data

import com.example.todo.di.models.BaseUrlProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RetrofitClient @Inject constructor(
    private val baseUrlProvider: BaseUrlProvider
) {
    val todoApi: TodoApi by lazy {
        val authInterceptor = AuthInterceptor("Wrosdon")
        Retrofit.Builder()
            .baseUrl(baseUrlProvider.getBaseUrl()) // Use injected provider
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .build()
            .create(TodoApi::class.java)
    }
}