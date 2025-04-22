package com.kds.moveamenable.data.repositories

import com.kds.moveamenable.api.RyotGraphQLClient
import com.kds.moveamenable.data.models.WorkoutEntry

object WorkoutRepository {
    suspend fun getAllWorkouts(): List<WorkoutEntry> {
        return RyotGraphQLClient.fetchWorkouts()
    }
}
