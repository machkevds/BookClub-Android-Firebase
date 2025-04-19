package com.kds.moveamenable.models

/**
 * Represents an exercise from the wger API
 * @property id Unique identifier
 * @property name Exercise name
 * @property description HTML-formatted instructions
 * @property category ID of the exercise category
 * @property muscles List of muscle IDs worked by this exercise
 * @property equipment List of equipment IDs needed for this exercise
 */
data class Exercise(
    val id: Int,
    val name: String?,
    val description: String?,
    val category: Int,
    val muscles: List<Int>,
    val equipment: List<Int> = emptyList()
)