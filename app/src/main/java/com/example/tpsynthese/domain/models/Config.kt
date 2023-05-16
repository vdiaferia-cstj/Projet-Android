package com.example.tpsynthese.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val mac:String = "",
    val SSID:String = "",
    val version: String = "",
    val kernel:List<String> = listOf(),
    val kernerRevision:String = "",
)
