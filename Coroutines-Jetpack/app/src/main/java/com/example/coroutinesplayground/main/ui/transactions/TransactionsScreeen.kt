package com.example.coroutinesplayground.main.ui.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutinesplayground.main.domain.model.Transaction
import com.example.coroutinesplayground.main.navigation.MainNavigationActions
import com.example.coroutinesplayground.ui.core.LoadingViewComponent
import com.example.coroutinesplayground.util.convertTimestampToStringDate
import com.example.coroutinesplayground.util.extractMonthFromTimestamp
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat


@Composable
fun TransactionsScreen(
    navigationActions: MainNavigationActions
) {
    val transactionsViewModel: TransactionsViewModel = koinViewModel()
    val transactionUIState = transactionsViewModel.transactionUIState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TransactionHeaderComponent(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        if (transactionUIState.value.isLoading) {
            LoadingViewComponent()
        } else {
            TransactionListItemComponent(transactionUIState.value.transactionList,
                navigationActions = navigationActions)
        }
    }
}

@Composable
fun TransactionHeaderComponent(modifier: Modifier) {
    Text(
        text = "Transactions",
        fontSize = 28.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionListItemComponent(transactionList: List<Transaction>,
                                 navigationActions: MainNavigationActions) {
    val groupedTransactions = transactionList.groupBy { extractMonthFromTimestamp(it.transactionDate) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        groupedTransactions.forEach { (initial, transactionsForInitial) ->
            stickyHeader {
                TransactionStickyHeaderComponent(textHeader = initial)
            }
            for (transaction in transactionsForInitial) {
                item(
                    key = transaction.id,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth().clickable {
                                navigationActions.navigateToTransactionDetails(transactionID = transaction.id)
                            }
                    ) {
                        TransactionTitleComponent(transaction = transaction)
                        TransactionMerchantDataComponent(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionStickyHeaderComponent(textHeader: String) {
    Text(text = textHeader,
        modifier = Modifier.fillMaxWidth().background(Color.White),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TransactionTitleComponent(transaction: Transaction) {
    Row {
        Text(
            text = "${transaction.title}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
            )
        Text(
            text = "-${ DecimalFormat("#.##").format(transaction.amount)} ${transaction.currency}",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.End,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TransactionMerchantDataComponent(transaction: Transaction) {
    Row {
        Text(
            text = "${transaction.merchant.city}, ${transaction.merchant.country}",
            fontSize = 16.sp,
        )
        Text(
            text = convertTimestampToStringDate(transaction.transactionDate),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.End,
            color = Color.DarkGray
        )
    }

}