package com.kds.moveamenable.models

/**
 * Represents a muscle group
 * @property id Unique identifier
 * @property name Muscle name
 * @property is_front Whether this is a front-facing muscle
 */
data class Muscle(
    val id: Int,
    val name: String?,
    val is_front: Boolean
)