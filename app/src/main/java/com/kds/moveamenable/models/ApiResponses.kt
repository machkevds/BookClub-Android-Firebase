package com.kds.moveamenable.models

/**
 * Base API response structure for wger endpoints
 */
data class ApiResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)

// response types
typealias ExerciseResponse = ApiResponse<Exercise>
typealias CategoryResponse = ApiResponse<ExerciseCategory>
typealias MuscleResponse = ApiResponse<Muscle>