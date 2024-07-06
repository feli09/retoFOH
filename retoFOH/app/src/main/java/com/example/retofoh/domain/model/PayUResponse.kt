package com.example.retofoh.domain.model

data class PaymentResponse(
    val code: String,
    val transactionResponse: TransactionResponse
)

data class TransactionResponse(
    val orderId: Long,
    val transactionId: String,
    val state: String,
    val trazabilityCode: String,
    val authorizationCode: String,
    val responseCode: String,
    val responseMessage: String,
    val operationDate: Long
)
