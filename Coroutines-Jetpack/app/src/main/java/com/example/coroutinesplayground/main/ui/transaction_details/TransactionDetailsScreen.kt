package com.example.coroutinesplayground.main.ui.transaction_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutinesplayground.main.domain.model.Transaction
import com.example.coroutinesplayground.shared.ui.core.LoadingViewComponent
import com.example.coroutinesplayground.shared.util.convertTimestampToStringDate
import com.example.coroutinesplayground.shared.util.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionDetailsScreen(transactionID: String?) {
    val transactionDetailsViewModel: TransactionDetailsViewModel = koinViewModel()
    transactionID?.let {
        transactionDetailsViewModel.onEvent(Event.LoadTransactionDetails(transactionID = it))
        val state = transactionDetailsViewModel.state.collectAsState()
        TransactionDetailsComponent(transactionDetailsUIState = state.value)
    }
}

@Composable
fun TransactionDetailsComponent(transactionDetailsUIState: TransactionDetailsUIState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        TransactionDetailsTitle()
        if (transactionDetailsUIState.isLoading) {
            LoadingViewComponent()
        } else {
            TransactionDetailsBodyComponent(transaction = transactionDetailsUIState.transaction)
        }
    }
}

@Composable
fun TransactionDetailsTitle() {
    Text(
        text = "Transaction Details",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )
}

@Composable
fun TransactionDetailsBodyComponent (transaction: Transaction) {
    Spacer(modifier = Modifier.height(32.dp))
    TransactionDetailsItem(labelText = "Title", valueText = transaction.title)
    TransactionDetailsItem(labelText = "Amount", valueText = "${formatAmount(transaction.amount.toString())} ${transaction.currency}")
    TransactionDetailsItem(labelText = "Date", valueText = convertTimestampToStringDate(transaction.transactionDate))
    TransactionDetailsItem(labelText = "Type", valueText = transaction.state)
    Spacer(modifier = Modifier.height(32.dp))
        Text(
        text = "Merchant details",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
    )
    TransactionDetailsItem(labelText = "Merchant", valueText = transaction.merchant.name)
    TransactionDetailsItem(labelText = "Location", valueText = "${transaction.merchant.city}, ${transaction.merchant.country}")
}

@Composable
fun TransactionDetailsItem(labelText: String, valueText: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        Text(
            text = labelText,
            fontSize = 22.sp
        )
        Text(
            text = valueText,
            fontSize = 22.sp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}