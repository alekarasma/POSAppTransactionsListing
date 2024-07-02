package com.example.cbaapplication.domain

import com.example.cbaapplication.data.model.Account
import javax.inject.Inject

class GetAccountInfoUseCase @Inject constructor(private val repository: IAccountRepository) {
    suspend operator fun invoke(): Result<Account> {
        return repository.getAccountInfo()
    }
}