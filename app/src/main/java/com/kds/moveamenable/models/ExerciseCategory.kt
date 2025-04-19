package com.kds.moveamenable.models

/**
 * Represents an exercise category (e.g., Chest, Arms)
 * @property id Unique identifier
 * @property name Category name
 */
data class ExerciseCategory(
    val id: Int,
    val name: String?
)