package com.example.coroutinesplayground.main.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesplayground.main.domain.data.TransactionsRepository
import com.example.coroutinesplayground.main.domain.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class TransactionsViewModel (
    private val transactionsRepository: TransactionsRepository
): ViewModel() {
    private val _state = MutableStateFlow(TransactionsUIState(true))
    val transactionUIState = _state

    init {
        onEvent(Event.LoadAllTransactions)
    }

    fun onEvent (event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.LoadAllTransactions -> loadAllTransactions()
                else -> {}
            }
        }
    }

    private suspend fun loadAllTransactions() {
        Timber.d("Load All Transactions started, In progress....")
        val transactions = transactionsRepository.getAllTransactions("1").sortedByDescending { it.transactionDate.toLong() }
        Timber.d("Load All Transactions finished")
        _state.emit(TransactionsUIState(isLoading = false, transactions))
    }
}

sealed class Event {
    object LoadAllTransactions : Event()
}

data class TransactionsUIState(
    val isLoading: Boolean = false,
    val transactionList: List<Transaction> = listOf()
)