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
                _isLoading.value = true
                _error.value = null

                // load reference data first
                _muscles.value = getMusclesWithFallback()
                _categories.value = getCategoriesWithFallback()

                //load exercises
                _exercises.value = getExercisesWithFallback()

            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
                //fallback 2 demo data
                _exercises.value = createDemoExercises()
                _categories.value = createDemoCategories()
                _muscles.value = createDemoMuscles()
            } finally {
                _isLoading.value = false
            }
        }
    }


    // fallback data creators
    private suspend fun getExercisesWithFallback(): List<Exercise> {
        return try {
            val apiExercises = ApiClient.instance.getExercises().results
            if (apiExercises.any { it.name != null }) apiExercises
            else createDemoExercises()
        } catch (e: Exception) {
            createDemoExercises()
        }
    }

    private suspend fun getCategoriesWithFallback(): List<ExerciseCategory> {
        return try {
            ApiClient.instance.getCategories().results.ifEmpty {
                createDemoCategories()
            }
        } catch (e: Exception) {
            createDemoCategories()
        }
    }

    private suspend fun getMusclesWithFallback(): List<Muscle> {
        return try {
            ApiClient.instance.getMuscles().results.ifEmpty {
                createDemoMuscles()
            }
        } catch (e: Exception) {
            createDemoMuscles()
        }
    }

    // demo data
    private fun createDemoExercises(): List<Exercise> {
        return listOf(
            Exercise(
                id = 1,
                name = "Bench Press",
                description = "Lie on a bench and press the barbell upward",
                category = 1,
                muscles = listOf(1, 2),
                equipment = listOf(1)
            ),
            Exercise(
                id = 2,
                name = "Squat",
                description = "Lower your body by bending knees then stand back up",
                category = 2,
                muscles = listOf(3, 4),
                equipment = listOf(1)
            )
        )
    }

    private fun createDemoCategories(): List<ExerciseCategory> {
        return listOf(
            ExerciseCategory(id = 1, name = "Chest"),
            ExerciseCategory(id = 2, name = "Legs"),
            ExerciseCategory(id = 3, name = "Back")
        )
    }

    private fun createDemoMuscles(): List<Muscle> {
        return listOf(
            Muscle(id = 1, name = "Pectorals", is_front = true),
            Muscle(id = 2, name = "Triceps", is_front = true),
            Muscle(id = 3, name = "Quadriceps", is_front = true),
            Muscle(id = 4, name = "Glutes", is_front = false)
        )
    }

    // filter functions
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