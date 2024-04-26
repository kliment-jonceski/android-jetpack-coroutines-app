package com.example.coroutinesplayground.main.ui.transaction_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesplayground.main.domain.data.TransactionsRepository
import com.example.coroutinesplayground.main.domain.model.Merchant
import com.example.coroutinesplayground.main.domain.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class TransactionDetailsViewModel (
    private val transactionsRepository: TransactionsRepository
): ViewModel() {
    private val _state = MutableStateFlow(TransactionDetailsUIState(isLoading = true))
    val state = _state

    fun onEvent(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.LoadTransactionDetails -> {
                    loadTransactionDetails(event.transactionID)
                }
            }
        }
    }

    private suspend fun loadTransactionDetails(transactionID: String) {
        Timber.d("Loading Transaction details...")
        val transaction = transactionsRepository.getTransactionDetails(transactionID = transactionID)
        Timber.d("Loading Transaction Finished. Emit new transaction.")
        _state.emit(TransactionDetailsUIState(isLoading = false, transaction = transaction))
    }
}

sealed class Event {
    data class LoadTransactionDetails(val transactionID: String) : Event()
}

data class TransactionDetailsUIState(
    val isLoading: Boolean = false,
    val transaction: Transaction = Transaction("", "", "", 0.0, "", "", Merchant("", "", ""))
)