package com.example.coroutinesplayground.main.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.coroutinesplayground.main.domain.model.Transaction
import com.example.coroutinesplayground.main.ui.transactions.TransactionMerchantDataComponent
import com.example.coroutinesplayground.main.ui.transactions.TransactionTitleComponent
import com.example.coroutinesplayground.shared.ui.core.LoadingViewComponent
import com.example.coroutinesplayground.shared.util.formatAmount
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun DashboardScreen() {
    val dashboardViewModel: DashboardViewModel  = koinViewModel()
    val balanceUiState = dashboardViewModel.balanceState.collectAsState()
    val latestTransactionsUIState = dashboardViewModel.latestTransactionsState.collectAsState()

    //Uncomment to use activity lifecycle events
    //triggerLoadingDataOnLifecycleEvent(dashboardViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 32.dp),
    ) {
        LogoComponent(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        BalanceComponent(balanceUiState.value, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(64.dp))
        LatestTransactionsComponent(latestTransactionsUIState = latestTransactionsUIState.value)
    }
}

@Composable
fun LogoComponent(modifier: Modifier) {
    Row (modifier = modifier
        .padding(vertical = 16.dp)) {
        Text(text = "MY",
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            color = Color.Blue
        )
        Text(text = "BANK",
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun BalanceComponent(balanceUIState: BalanceUIState, modifier: Modifier) {
    AvailableBalanceComponent(balanceUIState = balanceUIState, modifier = modifier)
    PendingBalanceComponent(balanceUIState = balanceUIState, modifier = modifier)
}

@Composable
fun AvailableBalanceComponent(balanceUIState: BalanceUIState, modifier: Modifier) {
    Spacer(modifier = Modifier.height(24.dp))

    if (balanceUIState.isLoading) {
        LoadingViewComponent()
    } else {
        val balance = balanceUIState.balance
        Text(
            "${formatAmount(balance.availableAmount)} ${balance.currency}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Available",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Green,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PendingBalanceComponent(balanceUIState: BalanceUIState, modifier: Modifier) {
    Spacer(modifier = Modifier.height(16.dp))
    if (balanceUIState.isLoading) {
        LoadingViewComponent()
    } else {
        val pendingAmount = balanceUIState.balance.reserved.toIntOrNull()
        if (pendingAmount != null && pendingAmount > 0) {
            Text(
                "${formatAmount(balanceUIState.balance.reserved)} ${balanceUIState.balance.currency}",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Pending",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LatestTransactionsComponent(latestTransactionsUIState: LatestTransactionsUIState) {
    ElevatedCard(
    ) {
        if (latestTransactionsUIState.isLoading) {
            LoadingViewComponent()
        } else {
            Column(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                LatestTransactionsTitle()
                Spacer(modifier = Modifier.height(4.dp))
                TransactionsComponent(latestTransactionsUIState.latestTransactions)
            }
        }
    }
}

@Composable
fun LatestTransactionsTitle() {
    Text(
        text = "Latest",
        fontSize = 18.sp
    )
}

@Composable
fun TransactionsComponent(transactions: List<Transaction>) {
    for (transaction in transactions) {
        Spacer(modifier = Modifier.height(8.dp))
        TransactionTitleComponent(transaction = transaction)
        TransactionMerchantDataComponent(transaction = transaction)
    }
}

@Composable
fun triggerLoadingDataOnLifecycleEvent(dashboardViewModel: DashboardViewModel) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_START) {
            dashboardViewModel.onEvent(Event.OnLoadBalance)
        }
    }
}