package com.example.tpsynthese.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val latitude:Float,
    val longtitude:Float,
)
