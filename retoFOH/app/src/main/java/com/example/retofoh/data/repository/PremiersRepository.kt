package com.example.retofoh.data.repository

import com.example.retofoh.data.service.RetrofitInstancePremier
import com.example.retofoh.domain.model.movie

class PremiersRepository() {
    private val api = RetrofitInstancePremier.api

    suspend fun getPremiere(): List<movie> {
        return api.getPremiere().premieres
    }
}