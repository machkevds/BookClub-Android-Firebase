package com.kds.moveamenable.api

import com.kds.moveamenable.models.CategoryResponse
import com.kds.moveamenable.models.ExerciseResponse
import com.kds.moveamenable.models.MuscleResponse
import retrofit2.http.GET

interface WgerApiService {
    @GET("exercise/?language=2")
    suspend fun getExercises(): ExerciseResponse

    @GET("exercisecategory/")
    suspend fun getCategories(): CategoryResponse

    @GET("muscle/")
    suspend fun getMuscles(): MuscleResponse
}