package com.kds.moveamenable.models

data class ExerciseDetailResponse(
    val name: Map<String, String>,
    val description: Map<String, String>,
    val muscles: List<Muscle>,
    val equipment: List<Equipment>
)