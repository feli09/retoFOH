package com.example.retofoh.data.repository

import com.example.retofoh.data.service.RetrofitInstance
import com.example.retofoh.domain.model.PaymentResponse
import com.example.retofoh.domain.model.PaymentTransaction
import com.example.retofoh.domain.model.movie

class PayURepository() {
    private val api = RetrofitInstance.api

    suspend fun postPayU(paymentTransaction: PaymentTransaction): PaymentResponse {
        return api.postPayU(paymentTransaction)
    }
}