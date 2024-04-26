package com.example.coroutinesplayground.main.domain.model


class Transaction (
    val id: String,
    val state: String,
    val title: String,
    val amount: Double,
    val currency: String,
    val transactionDate: String,
    val merchant: Merchant
)

class Merchant(
    val name: String,
    val city: String,
    val country: String
)