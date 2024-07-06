package com.example.retofoh.data.service

import com.example.retofoh.domain.model.ComboResponse
import com.example.retofoh.domain.model.PremiereResponse
import com.example.retofoh.domain.model.Complete
import com.example.retofoh.domain.model.PaymentResponse
import com.example.retofoh.domain.model.PaymentTransaction
import com.example.retofoh.domain.model.ReponseComplete
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("premieres")
    suspend fun getPremiere(): PremiereResponse
    @GET("candystore")
    suspend fun getcandys(): ComboResponse
    @POST("complete")
    suspend fun postComplete(@Body complete: Complete): ReponseComplete

    @POST("validpayu")
    suspend fun postPayU(@Body paymentTransaction: PaymentTransaction): PaymentResponse


}