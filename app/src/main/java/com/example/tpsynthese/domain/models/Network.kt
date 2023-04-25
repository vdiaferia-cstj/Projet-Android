package com.example.tpsynthese.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val nextReboot:String,
    val updateDate:String,
    val networkNode: NetworkNode
)
