package com.kds.moveamenable.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kds.moveamenable.data.models.WorkoutEntry
import com.kds.moveamenable.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val _workouts = MutableStateFlow<List<WorkoutEntry>>(emptyList())
    val workouts: StateFlow<List<WorkoutEntry>> = _workouts

    init {
        loadWorkouts()
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            _workouts.value = WorkoutRepository.getAllWorkouts()
        }
    }
}
