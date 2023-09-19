package com.mullatoes.todoapp.model

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: String,
    val dueDate: String,
    val category: String
)
