package com.example.cbaapplication

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    fun getCategoryIcon(category: String): Int {
        return when (category) {
            "business" -> R.drawable.icon_category_business
            "cards" -> R.drawable.icon_category_cards
            "cash" -> R.drawable.icon_category_cash
            "categories" -> R.drawable.icon_category_categories
            "eatingOut" -> R.drawable.icon_category_eating_out
            "education" -> R.drawable.icon_category_education
            "entertainment" -> R.drawable.icon_category_entertainment
            "groceries" -> R.drawable.icon_category_groceries
            "shopping" -> R.drawable.icon_category_shopping
            "transport" -> R.drawable.icon_category_transportation
            "travel" -> R.drawable.icon_category_travel
            "uncategorised" -> R.drawable.icon_category_uncategorised
            else -> R.drawable.icon_category_uncategorised // fallback drawable
        }
    }

    private fun daysBetween(startDate: Date?, endDate: Date?): Long {
        if (startDate == null || endDate == null) return 0
        val diff = endDate.time - startDate.time
        return diff / (1000 * 60 * 60 * 24)
    }

    fun formatDate(transactionDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE d MMM", Locale.getDefault())
        val date = inputFormat.parse(transactionDate)
        return date?.let { outputFormat.format(it) } ?: "Date Unknown"
    }


    fun daysAgo(transactionDate: String): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val transactionDateLocal = inputFormat.parse(transactionDate)

        val currentDate = Calendar.getInstance().time
        val daysDifference = daysBetween(transactionDateLocal, currentDate)

        return when {
            daysDifference == 0L -> "Today"
            daysDifference == 1L -> "Yesterday"
            daysDifference > 1L -> "$daysDifference days ago"
            else -> "Date Unknown"
        }
    }

    fun formatCurrency(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        val formattedAmount = formatter.format(Math.abs(amount)) // Format the absolute value
        return if (amount < 0) {
            "-$formattedAmount" // Add negative sign manually
        } else {
            formattedAmount
        }
    }

}