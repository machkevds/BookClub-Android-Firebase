package com.kds.moveamenable.api

object RyotQueries {
    const val GET_WORKOUTS = """
        query {
            workouts {
                id
                user
                name
                sets
                reps
                createdAt
            }
        }
    """

    fun createWorkout(name: String, sets: Int, reps: Int): String = """
        mutation {
            createWorkout(input: {
                name: "$name",
                sets: $sets,
                reps: $reps
            }) {
                id
                name
                sets
                reps
                user
                createdAt
            }
        }
    """
}
