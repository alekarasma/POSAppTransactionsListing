package com.example.cbaapplication.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.cbaapplication.Utils
import com.example.cbaapplication.Utils.daysAgo
import com.example.cbaapplication.data.model.Account
import com.example.cbaapplication.data.model.Transaction
import com.example.cbaapplication.ui.texColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionsScreen() {
    val viewModel: SharedViewModel = hiltViewModel()
    val accountInfo = viewModel.accountInfo.observeAsState()
    val transactions by viewModel.transactions.collectAsState()
    val pendingAmount by viewModel.pendingAmount.observeAsState(initial = 0.0)

    LaunchedEffect(Unit) {
        viewModel.getAccountInfo()
        viewModel.getTransactions()
        viewModel.calculatePendingAmount()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = accountInfo.value?.accountName ?: "",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LazyColumn {
                item {
                    DisplayAccountInfo(accountInfo = accountInfo, pendingAmount)
                }

                itemsIndexed(transactions.keys.toList()) { index, date ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = Utils.formatDate(date),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = daysAgo(date),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    ListTransactions(transactions.getValue(date))
                }
            }
        }
    }
}

@Composable
fun DisplayAccountInfo(accountInfo: State<Account?>, pendingAmount: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "Available",
            fontSize = 18.sp,
            color = texColor
        )
        Text(
            text = Utils.formatCurrency(accountInfo.value?.available?.toDouble() ?: 0.0),
            fontSize = 32.sp,
            modifier = Modifier.padding(4.dp),
            color = texColor
        )
        Text(
            text = "Balance  ${Utils.formatCurrency(accountInfo.value?.balance?.toDouble() ?: 0.0)}",
            modifier = Modifier.padding(4.dp),
            fontSize = 18.sp,
            color = texColor,
        )
        Text(
            text = "Pending  ${Utils.formatCurrency(pendingAmount)}",
            modifier = Modifier.padding(4.dp),
            fontSize = 18.sp,
            color = texColor
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        Text(
            text = "BSB ${accountInfo.value?.bsb}        Account ${accountInfo.value?.accountNumber}",
            modifier = Modifier.padding(12.dp),
            fontSize = 16.sp,
            color = texColor
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
    }
}


@Composable
fun ListTransactions(transaction: List<Transaction>) {
    transaction.forEach {
        val resourceId = Utils.getCategoryIcon(it.category)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = resourceId),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
            Text(
                text = it.description,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            )
            Text(text = Utils.formatCurrency(it.amount.toDouble()), fontSize = 18.sp)
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 16.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview
@Composable
fun CallListTransaction() {
}





