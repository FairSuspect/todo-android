package com.example.todo.data


import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token") // Замените "Bearer" на нужный тип авторизации
            .build()
        return chain.proceed(authenticatedRequest)
    }
}