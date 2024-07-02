package com.example.cbaapplication.domain

import com.example.cbaapplication.data.model.Transaction
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(private val repository: IAccountRepository) {

    suspend operator fun invoke(): Result<Map<String, List<Transaction>>> {
        return repository.getTransactions()
    }
}