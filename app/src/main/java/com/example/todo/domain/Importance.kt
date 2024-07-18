package com.example.todo.domain

enum class Importance {
    LOW, BASIC, IMPORTANT;

    fun getDisplayName(): String {
        return when (this) {
            LOW -> "Нет"
            BASIC -> "Низкий"
            IMPORTANT -> "Высокий"
        }
    }
}