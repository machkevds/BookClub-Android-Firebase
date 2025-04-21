package com.kds.moveamenable.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kds.moveamenable.api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.kds.moveamenable.models.Exercise
import com.kds.moveamenable.models.ExerciseCategory
import com.kds.moveamenable.models.Muscle

class ExerciseViewModel : ViewModel() {
    // sate flows for all data
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    private val _categories = MutableStateFlow<List<ExerciseCategory>>(emptyList())
    private val _muscles = MutableStateFlow<List<Muscle>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    //public exposed flows
    val exercises: StateFlow<List<Exercise>> = _exercises
    val categories: StateFlow<List<ExerciseCategory>> = _categories
    val muscles: StateFlow<List<Muscle>> = _muscles
    val isLoading: StateFlow<Boolean> = _isLoading
    val error: StateFlow<String?> = _error

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            try {
                println("[DEBUG] Coroutine started")
                _isLoading.value = true
                _error.value = null

                // load reference data first
                println("[DEBUG] Fetching categories...")
                _categories.value = getCategoriesWithFallback().also {
                    println("[DEBUG] Got ${it.size} categories")
                }

                println("[DEBUG] Fetching muscles...")
                _muscles.value = getMusclesWithFallback()

                //load exercises
                println("[DEBUG] Fetching exercises...")
                _exercises.value = getExercisesWithFallback().also {
                    println("[DEBUG] Got ${it.size} exercises")
                }

            } catch (e: Exception) {
                println("[DEBUG] FAILURE: ${e.javaClass.simpleName} - ${e.message}")
                e.printStackTrace()
                _error.value = "Data loading failed"

            } finally {
                println("[DEBUG] Loading complete")
                _isLoading.value = false
            }
        }
    }


    // fallback data creators
    private suspend fun getExercisesWithFallback(): List<Exercise> {
        return try {
            println("[DEBUG] Attempting API exercise fetch...")
            val apiResponse = ApiClient.instance.getExercises()
            val apiExercises = apiResponse.results

            // Enhanced debug logging
            println("[DEBUG] Raw API response sample: ${apiResponse.results.take(3)}")

            // Try alternative name field if primary is null
            val exercisesWithNames = apiExercises.mapNotNull { apiExercise ->
                when {
                    !apiExercise.name.isNullOrBlank() -> apiExercise
                    apiExercise.id != null -> {
                        // If name is null but ID exists, create minimal valid exercise
                        Exercise(
                            id = apiExercise.id,
                            name = "Exercise ${apiExercise.id}",
                            originalName = null,  // Add this line,
                            description = "Description not available",
                            category = apiExercise.category ?: -1,
                            muscles = apiExercise.muscles,
                            equipment = apiExercise.equipment
                        )
                    }
                    else -> null
                }
            }
            if (exercisesWithNames.isNotEmpty()) {
                println("[DEBUG] Using ${exercisesWithNames.size} API exercises")
                exercisesWithNames
            } else {
                getDemoExercises()
            }
        } catch (e: Exception) {
            println("[DEBUG] API Error: ${e.stackTraceToString()}")
            getDemoExercises()
        }
    }

    private suspend fun getCategoriesWithFallback(): List<ExerciseCategory> {
        return try {
            ApiClient.instance.getCategories().results.ifEmpty {
                getDemoCategories()
            }
        } catch (e: Exception) {
            getDemoCategories()
        }
    }

    private suspend fun getMusclesWithFallback(): List<Muscle> {
        return try {
            ApiClient.instance.getMuscles().results.ifEmpty {
                getDemoMuscles()
            }
        } catch (e: Exception) {
            getDemoMuscles()
        }
    }

    //demo data functions
    private fun getDemoExercises(): List<Exercise> {
        return listOf(
            Exercise(
                id = -1, // Flag as demo data
                name = "Bench Press",
                originalName = null,  // Add this line
                description = "Lie on a bench and press the barbell upward",
                category = 1,
                muscles = listOf(1, 2),
                equipment = listOf(1)
            ),
            Exercise(
                id = -2,
                name = "Squat",
                originalName = null,  // Add this line
                description = "Lower your body by bending knees then stand back up",
                category = 2,
                muscles = listOf(3, 4),
                equipment = listOf(1)
            )
        )
    }

    private fun getDemoCategories(): List<ExerciseCategory> {
        return listOf(
            ExerciseCategory(id = 1, name = "Chest"),
            ExerciseCategory(id = 2, name = "Legs"),
            ExerciseCategory(id = 3, name = "Back")
        )
    }

    private fun getDemoMuscles(): List<Muscle> {
        return listOf(
            Muscle(id = 1, name = "Pectorals", is_front = true),
            Muscle(id = 2, name = "Triceps", is_front = true),
            Muscle(id = 3, name = "Quadriceps", is_front = true),
            Muscle(id = 4, name = "Glutes", is_front = false)
        )
    }


    // Filter Functions
    fun getExercisesByCategory(categoryId: Int): List<Exercise> {
        return _exercises.value.filter { it.category == categoryId }
    }

    fun getExercisesByMuscle(muscleId: Int): List<Exercise> {
        return _exercises.value.filter { muscleId in it.muscles }
    }

    fun clearError() {
        _error.value = null
    }
}