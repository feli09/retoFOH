package com.example.retofoh.data.repository

import com.example.retofoh.data.service.RetrofitInstance
import com.example.retofoh.domain.model.Combo

class CandyRepository() {
    private val api = RetrofitInstance.api

    suspend fun getCandy(): List<Combo> {
        return api.getcandys().items
    }
}