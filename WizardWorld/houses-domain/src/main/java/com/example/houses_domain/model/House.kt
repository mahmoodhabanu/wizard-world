package com.example.houses_domain.model

import kotlinx.serialization.Serializable

@Serializable
data class House(
    val id: String,
    val name: String,
    val houseColours: String,
    val animal: String,
    val founder: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    val heads: List<Head>,
    val traits: List<Trait>
)

@Serializable
data class Head(
    val id: String,
    val firstName: String,
    val lastName: String
)

@Serializable
data class Trait(
    val id: String,
    val name: String
)


