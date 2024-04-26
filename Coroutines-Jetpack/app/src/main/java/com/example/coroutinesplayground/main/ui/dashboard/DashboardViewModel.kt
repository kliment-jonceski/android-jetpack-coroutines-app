package com.example.coroutinesplayground.main.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesplayground.main.domain.data.BalanceRepository
import com.example.coroutinesplayground.main.domain.data.TransactionsRepository
import com.example.coroutinesplayground.main.domain.model.Balance
import com.example.coroutinesplayground.main.domain.model.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DashboardViewModel (
    private val balanceRepository: BalanceRepository,
    private val transactionsRepository: TransactionsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val _balanceState: MutableStateFlow<BalanceUIState> = MutableStateFlow(BalanceUIState(isLoading = true))
    private val _latestTransactionsState: MutableStateFlow<LatestTransactionsUIState> = MutableStateFlow(
        LatestTransactionsUIState(isLoading = true)
    )
    val balanceState: StateFlow<BalanceUIState> = _balanceState
    val latestTransactionsState = _latestTransactionsState

    init {
        viewModelScope.launch {
            loadBalance()
            loadLatestTransactions()
        }
    }
    fun onEvent (event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.OnLoadBalance -> loadBalance()
                is Event.OnLoadLatestTransactions -> loadLatestTransactions()
            }
        }
    }

    private suspend fun loadBalance() {
        Timber.d("LoadBalance started, In progress....")
        val balance = balanceRepository.getBalance("2")
        Timber.d("LoadBalance Finished")
        _balanceState.emit(BalanceUIState(isLoading = false, balance = balance))
        //_state.emit(DashBoardUiState.BalanceLoaded(balance = balance))
    }

    private suspend fun loadLatestTransactions() {
        Timber.d("LoadLatestTransactions started, In progress....")
        val latestTransactions = transactionsRepository.getLatestTransactions("2", 2)
        Timber.d("LoadLatestTransactions finished. Emit latest transactions.")
        _latestTransactionsState.emit(LatestTransactionsUIState(isLoading = false, latestTransactions))
    }

}
sealed class Event {
    object OnLoadBalance : Event()
    object OnLoadLatestTransactions: Event()
}

//sealed class DashBoardUiState {
//    object Loading: DashBoardUiState()
//    data class BalanceLoaded (val balance: Balance): DashBoardUiState()
//}

data class BalanceUIState(
    val isLoading: Boolean = false,
    val balance: Balance = Balance("", "", "", "")
)

data class LatestTransactionsUIState(
    val isLoading: Boolean = false,
    val latestTransactions: List<Transaction> = listOf()
)