package com.example.cbaapplication.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Account(
    val bsb: String,
    val accountNumber: String,
    val balance: String,
    val available: String,
    val accountName: String
)