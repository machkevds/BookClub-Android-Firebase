package com.kds.moveamenable.data.models

data class WorkoutEntry(
    val id: String,
    val user: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val createdAt: String
)
