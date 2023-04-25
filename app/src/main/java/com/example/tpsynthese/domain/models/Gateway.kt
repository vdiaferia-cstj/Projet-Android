package com.example.tpsynthese.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Gateway(
    val href:String,
    val serialNumber:String,
    val revision:String,
    val pin:String,
    val hash:String,
    val Customer:Customer,
    val config: Config,
    val connection: Connection
)
