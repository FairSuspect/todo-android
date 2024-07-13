package com.example.todo.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    val todoApi: TodoApi by lazy {
    val authInterceptor = AuthInterceptor("Wrosdon")
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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