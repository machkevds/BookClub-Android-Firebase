package com.kds.moveamenable.models

import com.google.gson.annotations.SerializedName

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
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("name_original")
    val originalName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("muscles")
    val muscles: List<Int>,
    @SerializedName("secondary_muscles")
    val secondaryMuscles: List<Int> = emptyList(),
    @SerializedName("equipment")
    val equipment: List<Int> = emptyList(),
    @SerializedName("exercise_base")  // If needed for nested structure
    val exerciseBase: Int? = null,
    @SerializedName("license_author")
    val author: String? = null,
    @SerializedName("license")
    val license: Int? = null,
    @SerializedName("uuid")
    val uuid: String? = null,


) {
    fun displayName(): String {
        return name ?: originalName ?: "Exercise $id"
    }

    // Helper function for debugging
    fun debugString(): String {
        return "Exercise(id=$id, name=${name ?: "null"}, category=$category, muscles=${muscles.size})"
    }

}