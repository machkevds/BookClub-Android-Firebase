package com.kds.moveamenable.api

import com.kds.moveamenable.models.ApiResponse
import com.kds.moveamenable.models.CategoryResponse
import com.kds.moveamenable.models.Exercise
import com.kds.moveamenable.models.ExerciseCategory
import com.kds.moveamenable.models.ExerciseDetailResponse
import com.kds.moveamenable.models.ExerciseResponse
import com.kds.moveamenable.models.Muscle
import com.kds.moveamenable.models.MuscleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WgerApiService {
    @GET("exercise/")
    suspend fun getExercises(
        @Query("language") language: Int = 2, // English
        @Query("status") status: String = "2", // Approved exercises only
        @Query("limit") limit: Int = 100
    ): ApiResponse<Exercise>

    @GET("exercise/{id}/")
    suspend fun getExerciseDetails(
        @Path("id") id: Int,
        @Query("language") language: Int = 2
    ): Exercise

    @GET("exercisecategory/")
    suspend fun getCategories():  ApiResponse<ExerciseCategory>

    @GET("muscle/")
    suspend fun getMuscles(): ApiResponse<Muscle>
}