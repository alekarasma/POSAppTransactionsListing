package com.example.cbaapplication.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cbaapplication.data.model.Transaction
import com.example.cbaapplication.domain.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.cbaapplication.domain.Result
import com.example.cbaapplication.data.model.Account
import com.example.cbaapplication.domain.GetAccountInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase
) :
    ViewModel() {
    private val _transactions = MutableStateFlow<Map<String, List<Transaction>>>(emptyMap())
    val transactions: StateFlow<Map<String, List<Transaction>>> get() = _transactions
    private val _accountInfo = MutableLiveData<Account?>()
    val accountInfo: LiveData<Account?> get() = _accountInfo
    private val _pendingAmount = MutableLiveData<Double>()
    val pendingAmount: LiveData<Double> get() = _pendingAmount

    fun getTransactions() {
        viewModelScope.launch {
            when (val result: Result<Map<String, List<Transaction>>> = getTransactionsUseCase()) {
                is Result.Success -> {
                    _transactions.value = result.data
                }

                is Result.Error -> {
                }
            }
        }
    }

    fun getAccountInfo() {
        viewModelScope.launch {
            when (val result: Result<Account> = getAccountInfoUseCase()) {
                is Result.Success -> {
                    _accountInfo.value = result.data
                }

                is Result.Error -> {

                }
            }
        }
    }

    fun calculatePendingAmount() {
        viewModelScope.launch {
            val pendingAmount = transactions.value.values.flatten()
                .filter { it.isPending }
                .sumByDouble { it.amount.toDouble() }
            Log.i("PendingAmount", "Pending amount: $pendingAmount")
            _pendingAmount.value = pendingAmount
        }

    }
}