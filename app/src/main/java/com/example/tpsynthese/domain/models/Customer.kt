package com.example.tpsynthese.domain.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val href:String ="",
    val firstName:String = "",
    val lastName:String ="",
    val email:String ="",
    val address: String ="",
    val city:String ="",
    val country:String= "",
    val postalCode:String ="",
    val phone:String ="",
    //@SerialName("coord")
    val coord: Coordonate = Coordonate(0f,0f)
)
