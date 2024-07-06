package com.example.retofoh.domain.model


data class PremiereResponse(
    val premieres: List<movie>
)
data class movie(
    val description: String,
    val image: String
)
