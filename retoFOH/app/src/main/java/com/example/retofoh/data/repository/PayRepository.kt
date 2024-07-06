package com.example.retofoh.data.repository

import com.example.retofoh.data.service.RetrofitInstance
import com.example.retofoh.domain.model.Complete
import com.example.retofoh.domain.model.ReponseComplete

class PayRepository() {
    private val api = RetrofitInstance.api

    suspend fun postComplete(complete: Complete): ReponseComplete {
        return api.postComplete(complete)
    }
}