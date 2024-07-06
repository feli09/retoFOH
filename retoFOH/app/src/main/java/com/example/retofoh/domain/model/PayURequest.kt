package com.example.retofoh.domain.model

data class PaymentTransaction(
    val test: Boolean,
    val language: String,
    val command: String,
    val merchant: MerchantInfo,
    val transaction: TransactionInfo
)

data class MerchantInfo(
    val apiKey: String,
    val apiLogin: String
)

data class TransactionInfo(
    val order: OrderInfo,
    val payer: PayerInfo,
    val creditCard: CreditCardInfo,
    val type: String,
    val paymentMethod: String,
    val paymentCountry: String
)

data class OrderInfo(
    val language: String,
    val additionalValues: AdditionalValues
)

data class AdditionalValues(
    val TX_VALUE: TxValue
)

data class TxValue(
    val value: String,
    val currency: String
)

data class PayerInfo(
    val fullName: String,
    val emailAddress: String,
    val contactPhone: String,
    val dniNumber: String
)

data class CreditCardInfo(
    val number: String,
    val securityCode: String,
    val expirationDate: String,
    val name: String
)

