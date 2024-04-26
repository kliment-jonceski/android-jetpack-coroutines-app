package com.example.coroutinesplayground.main.di

import com.example.coroutinesplayground.main.domain.data.BalanceRepository
import com.example.coroutinesplayground.main.domain.data.FakeBalanceRepository
import com.example.coroutinesplayground.main.domain.data.FakeTransactionsRepository
import com.example.coroutinesplayground.main.domain.data.TransactionsRepository
import com.example.coroutinesplayground.main.ui.dashboard.DashboardViewModel
import com.example.coroutinesplayground.main.ui.transaction_details.TransactionDetailsViewModel
import com.example.coroutinesplayground.main.ui.transactions.TransactionsViewModel
import org.koin.dsl.module


val mainModule = module {
    single <BalanceRepository> { FakeBalanceRepository() }
    single <TransactionsRepository> { FakeTransactionsRepository() }
    single { DashboardViewModel(get(), get ()) }
    single { TransactionsViewModel(get ())}
    single{ TransactionDetailsViewModel (get ())}
}