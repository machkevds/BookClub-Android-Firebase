package com.kds.moveamenable.models

import com.google.gson.annotations.SerializedName

/**
 * Represents the base exercise data from the API
 * @property id Unique identifier
 * @property name Base exercise name
 * @property description Description of the base exercise
 */
data class ExerciseBase(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?
)