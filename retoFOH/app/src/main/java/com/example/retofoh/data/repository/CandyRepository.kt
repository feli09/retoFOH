package com.example.retofoh.data.repository

import com.example.retofoh.data.service.RetrofitInstanceCandy
import com.example.retofoh.domain.model.Combo

class CandyRepository() {
    private val api = RetrofitInstanceCandy.api

    suspend fun getCandy(): List<Combo> {
        return api.getcandys().items
    }
}