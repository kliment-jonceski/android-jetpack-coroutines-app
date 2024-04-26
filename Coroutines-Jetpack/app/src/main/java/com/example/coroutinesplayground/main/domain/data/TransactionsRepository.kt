package com.example.coroutinesplayground.main.domain.data

import com.example.coroutinesplayground.main.domain.model.Merchant
import com.example.coroutinesplayground.main.domain.model.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

interface TransactionsRepository {
    suspend fun getAllTransactions(userId: String) : List<Transaction>
    suspend fun getAllTransactionsStream(userId: String) : Flow<List<Transaction>>
    suspend fun getLatestTransactions(userId: String, numTransactions: Int) : List<Transaction>
    suspend fun getTransactionDetails(transactionID: String) : Transaction
}

class FakeTransactionsRepository : TransactionsRepository {
    override suspend fun getAllTransactions(userId: String): List<Transaction> {
        delay(2000)
        return listTransactionsUser1
    }

    override suspend fun getAllTransactionsStream(userId: String): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestTransactions(
        userId: String,
        numTransactions: Int
    ): List<Transaction> {
        delay(1500)
        return listTransactionsUser1.sortedByDescending {
            it.transactionDate.toLong()
        }.take(numTransactions)
    }

    override suspend fun getTransactionDetails(transactionID: String): Transaction {
        delay(1000)
        return listTransactionsUser1.first {
            it.id == transactionID
        }
    }
}

private fun generateSmallAmount(): Double = Random.nextDouble(1.0, 10.0)
private fun generateMediumAmount(): Double = Random.nextDouble(10.0, 150.0)
private fun generateLargeAmount(): Double = Random.nextDouble(200.0, 2000.0)

private fun generateRandomTimestamp(): Long {
    val currentInstant = Instant.now()
    val twoMonthsAgoInstant = currentInstant.minus(2 * 30L * 24L * 60L * 60L, ChronoUnit.SECONDS)
    val randomSeconds = Random.nextLong(twoMonthsAgoInstant.epochSecond, currentInstant.epochSecond)
    return randomSeconds
}

val listTransactionsUser1 = listOf(
    Transaction("1", "POSTED", "Coffee London", generateSmallAmount(), "GBP", generateRandomTimestamp().toString(),
        Merchant("London Hotel", "London", "GB")
    ),
    Transaction("2", "POSTED", "Coffee London", generateSmallAmount(), "GBP", generateRandomTimestamp().toString(),
        Merchant("London Cafe", "London", "GB")
    ),
    Transaction("3", "POSTED", "Pizza London", generateSmallAmount(), "GBP", generateRandomTimestamp().toString(),
        Merchant("London Restaurant", "London", "GB")
    ),
    Transaction("4", "POSTED", "Coffee New York", generateSmallAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("NY Coffee", "New York", "USA")
    ),
    Transaction("5", "POSTED", "Bar London", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Marco Polo", "New York", "USA")
    ),
    Transaction("6", "POSTED", "Old Town Bar", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Old Town Bar", "New York", "USA")
    ),
    Transaction("7", "PENDING", "Rent Car USA", generateLargeAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Rent Car USA", "New York", "USA")
    ),
    Transaction("8", "POSTED", "Fun Bar", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar", "New York", "USA")
    ),
    Transaction("9", "POSTED", "Fun Bar 1", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 1", "New York", "USA")
    ),
    Transaction("10", "POSTED", "Fun Bar 2", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 2", "New York", "USA")
    ),
    Transaction("11", "POSTED", "Fun Bar 3", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 3", "New York", "USA")
    ),
    Transaction("12", "POSTED", "Fun Bar 4", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 4", "New York", "USA")
    ),
    Transaction("13", "POSTED", "Fun Bar 5", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 5", "New York", "USA")
    ),
    Transaction("28", "POSTED", "Fun Bar", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar", "New York", "USA")
    ),
    Transaction("29", "POSTED", "Fun Bar 1", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 1", "New York", "USA")
    ),
    Transaction("30", "POSTED", "Fun Bar 2", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 2", "New York", "USA")
    ),
    Transaction("31", "POSTED", "Fun Bar 3", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 3", "New York", "USA")
    ),
    Transaction("32", "POSTED", "Fun Bar 4", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 4", "New York", "USA")
    ),
    Transaction("33", "POSTED", "Fun Bar 5", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 5", "New York", "USA")
    )
)

val listTransactionsUser2 = listOf<Transaction>(
    Transaction("14", "POSTED", "Coffee Zurich", generateSmallAmount(), "CHF", generateRandomTimestamp().toString(),
        Merchant("Zurich Hotel", "Zurich", "CHF")
    ),
    Transaction("15", "POSTED", "Coffee Zurich", generateSmallAmount(), "CHF", generateRandomTimestamp().toString(),
        Merchant("London Zurich", "Zurich", "CHF")
    ),
    Transaction("16", "POSTED", "Pizza Zurich", generateMediumAmount(), "CHF", generateRandomTimestamp().toString(),
        Merchant("Zurich Restaurant", "Zurich", "CHF")
    ),
    Transaction("17", "POSTED", "Pub Zurich", generateMediumAmount(), "CHF", generateRandomTimestamp().toString(),
        Merchant("PUB Zurich", "Zurich", "CHF")
    ),
    Transaction("18", "POSTED", "Bar London", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Marco Polo", "New York", "USA")
    ),
    Transaction("19", "POSTED", "Old Town Bar", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Old Town Bar", "New York", "USA")
    ),
    Transaction("20", "PENDING", "Rent Car USA", generateLargeAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Rent Car USA", "New York", "USA")
    ),
    Transaction("21", "POSTED", "Fun Bar", generateLargeAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar", "New York", "USA")
    ),
    Transaction("22", "POSTED", "Fun Bar 1", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 1", "New York", "USA")
    ),
    Transaction("23", "POSTED", "Fun Bar 2", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 2", "New York", "USA")
    ),
    Transaction("24", "POSTED", "Fun Bar 3", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 3", "New York", "USA")
    ),
    Transaction("25", "POSTED", "Fun Bar 4", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 4", "New York", "USA")
    ),
    Transaction("25", "POSTED", "Fun Bar 5", generateMediumAmount(), "USD", generateRandomTimestamp().toString(),
        Merchant("Fun NY Bar 5", "New York", "USA")
    )
)