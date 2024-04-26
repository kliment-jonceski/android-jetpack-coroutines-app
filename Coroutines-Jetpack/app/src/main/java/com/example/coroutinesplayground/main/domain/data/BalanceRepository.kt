package com.example.coroutinesplayground.main.domain.data

import com.example.coroutinesplayground.main.domain.model.Balance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    suspend fun getBalance(userId: String) : Balance
    suspend fun getBalanceStream(userId: String) : Flow<Balance>
}

class FakeBalanceRepository : BalanceRepository {

    override suspend fun getBalance(userId: String): Balance {
        delay(1500)
        return when (userId) {
            "1" -> Balance("1", "12385.45", "USD", "0")
            "2" -> Balance("2", "45250", "USD", "1210")
            "3" -> Balance("3", "15500", "EUR", "320")
            "4" -> Balance("4", "1505205", "USD", "790")
            else -> Balance("", "", "", "")
        }
    }

    override suspend fun getBalanceStream(userId: String): Flow<Balance> {
        TODO("Not yet implemented")
    }
}