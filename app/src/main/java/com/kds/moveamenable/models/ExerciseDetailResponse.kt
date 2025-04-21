package com.kds.moveamenable.models

import com.google.gson.annotations.SerializedName

data class ExerciseDetailResponse(
    val id: Int,
    val name: Map<String, String>?,
    val description: Map<String, String>?,
    val muscles: List<Muscle>,
    val equipment: List<Equipment>,
    @SerializedName("exercise_base")  // 🔥 This is critical
    val exerciseBase: ExerciseBase? = null

)