package com.example.coroutinesplayground.util

import java.text.DecimalFormat

fun formatAmount(amount: String): String {
    return DecimalFormat("#,###.##").format(amount.toDouble())
}