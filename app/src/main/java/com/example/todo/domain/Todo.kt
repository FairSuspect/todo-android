package com.example.todo.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

typealias TodoId = String

@Entity
data class Todo(
    @PrimaryKey
    val id: TodoId,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "done")
    val done: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "change_at")
    val changeAt: LocalDateTime? = null,
    @ColumnInfo(name = "importance")
    val importance: Importance = Importance.BASIC,
    @ColumnInfo(name = "last_updated_by")
    val lastUpdatedBy: String? = null,
    @ColumnInfo(name = "deadline")
    val deadline: LocalDateTime? = null,
)
