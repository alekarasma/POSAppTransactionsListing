package com.example.cbaapplication.domain

import com.example.cbaapplication.data.model.Transaction
import com.example.cbaapplication.data.model.Account

interface IAccountRepository {
    suspend fun getTransactions(): Result<Map<String, List<Transaction>>>
    suspend fun getAccountInfo(): Result<Account>
}