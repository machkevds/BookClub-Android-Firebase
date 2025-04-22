package com.kds.moveamenable.api

import com.kds.moveamenable.data.models.WorkoutEntry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class GraphQLRequest(val query: String)

@Serializable
data class GraphQLResponse<T>(val data: T?)

object RyotGraphQLClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private const val BASE_URL = "https://api.ryot.io/graphql" // replace if self-hosted

    suspend fun fetchWorkouts(): List<WorkoutEntry> {
        val response: HttpResponse = client.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(GraphQLRequest(RyotQueries.GET_WORKOUTS))
        }
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        return json["data"]?.jsonObject?.get("workouts")?.jsonArray?.map {
            WorkoutEntry(
                id = it.jsonObject["id"]!!.jsonPrimitive.content,
                user = it.jsonObject["user"]!!.jsonPrimitive.content,
                name = it.jsonObject["name"]!!.jsonPrimitive.content,
                sets = it.jsonObject["sets"]!!.jsonPrimitive.int,
                reps = it.jsonObject["reps"]!!.jsonPrimitive.int,
                createdAt = it.jsonObject["createdAt"]!!.jsonPrimitive.content
            )
        } ?: emptyList()
    }
}
