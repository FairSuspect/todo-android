package com.example.todo.domain

import java.time.LocalDateTime


data class Todo(
    val id: String,
    val text: String,
    val done: Boolean,
    val createdAt: LocalDateTime? = null,
    val changeAt: LocalDateTime? = null,
    val importance: Importance = Importance.BASIC,
    val lastUpdatedBy: String? = null,
    val deadline: LocalDateTime? = null,
)
