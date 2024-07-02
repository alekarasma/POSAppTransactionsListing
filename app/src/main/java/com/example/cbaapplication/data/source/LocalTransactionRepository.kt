package com.example.cbaapplication.data.source

import android.content.Context
import android.util.Log
import com.example.cbaapplication.R
import com.example.cbaapplication.domain.Result
import com.example.cbaapplication.data.model.Account
import com.example.cbaapplication.data.model.Transaction
import com.example.cbaapplication.domain.IAccountRepository
import org.json.JSONObject
import java.io.InputStream
import javax.inject.Inject

class LocalTransactionRepository @Inject constructor(private val context: Context) :
    IAccountRepository {
    val TAG = "LocalTransactionRepository"
    override suspend fun getTransactions(): Result<Map<String, List<Transaction>>> {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.exercise)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val transactionsArray = jsonObject.getJSONArray("transactions")
            val transactionMap = mutableMapOf<String, MutableList<Transaction>>()
            for (i in 0 until transactionsArray.length()) {
                val transactionObject = transactionsArray.getJSONObject(i)

                val id = transactionObject.getString("id")
                val amount = transactionObject.getString("amount")
                val isPending = transactionObject.getBoolean("isPending")
                val description = transactionObject.getString("description")
                val category = transactionObject.getString("category")
                val effectiveDate = transactionObject.getString("effectiveDate")

                val transaction =
                    Transaction(amount, id, isPending, description, category, effectiveDate)

                if (transactionMap.containsKey(effectiveDate)) {
                    transactionMap[effectiveDate]!!.add(transaction)
                } else {
                    transactionMap[effectiveDate] = mutableListOf(transaction)
                }
            }
            Result.Success(transactionMap)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing transactions list: $e")
            Result.Error(e)
        }

    }

    override suspend fun getAccountInfo(): Result<Account> {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.exercise)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val accountJson = jsonObject.getJSONObject("account")
            val accountInfo = Account(
                bsb = accountJson.getString("bsb"),
                accountNumber = accountJson.getString("accountNumber"),
                balance = accountJson.getString("balance"),
                available = accountJson.getString("available"),
                accountName = accountJson.getString("accountName")
            )
            Log.e(TAG, "AccountInfo: $accountInfo")
            Result.Success(accountInfo)
        } catch (e: Exception) {
            Log.e(TAG, "Error reading AccountInfo: $e")
            Result.Error(e)
        }
    }
}