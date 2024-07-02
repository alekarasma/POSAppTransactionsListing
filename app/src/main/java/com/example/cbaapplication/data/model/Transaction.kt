package com.example.cbaapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val amount: String,
    val id: String,
    val isPending: Boolean,
    val description: String,
    val category: String,
    val effectiveDate: String
)

@Serializable
data class ATM(
    val id: String,
    val name: String,
    val location: Location,
    val address: String
)

@Serializable
data class Location(
    val lat: Double,
    val lon: Double
)