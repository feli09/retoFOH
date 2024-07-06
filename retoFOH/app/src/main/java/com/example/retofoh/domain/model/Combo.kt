package com.example.retofoh.domain.model
data class ComboResponse(
    val items: List<Combo>
)

data class Combo(
    val name: String,
    val description: String,
    val price: String,
    val currency: String
)
