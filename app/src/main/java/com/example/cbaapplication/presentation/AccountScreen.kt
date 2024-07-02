package com.example.cbaapplication.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AccountScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(screenHeight * 0.2f)
                .fillMaxWidth(),
            onClick = { navController.navigate("Transactions") }
        ) {
            Text(text = "Complete Access")
        }
    }
}

