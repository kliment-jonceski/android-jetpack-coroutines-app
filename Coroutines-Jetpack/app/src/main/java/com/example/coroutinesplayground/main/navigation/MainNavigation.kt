package com.example.coroutinesplayground.main.navigation

import androidx.navigation.NavController
import com.example.coroutinesplayground.main.navigation.MainDestinationsArgs.TRANSACTION_ID_ARG
import com.example.coroutinesplayground.main.navigation.MainScreens.DASHBOARD_SCREEN
import com.example.coroutinesplayground.main.navigation.MainScreens.SETTINGS_SCREEN
import com.example.coroutinesplayground.main.navigation.MainScreens.TRANSACTION_DETAILS_SCREEN
import com.example.coroutinesplayground.main.navigation.MainScreens.TRANSACTIONS_SCREEN

private object MainScreens {
    const val DASHBOARD_SCREEN = "dashboardScreen"
    const val TRANSACTIONS_SCREEN = "transactionsScreen"
    const val SETTINGS_SCREEN = "settingsScreen"
    const val TRANSACTION_DETAILS_SCREEN = "transactionDetailsScreen"
}

object MainDestinationsArgs {
    const val TRANSACTION_ID_ARG = "transactionID"
}

object MainNavigationDestinations {
    const val MAIN_ROUTE ="main"
    const val DASHBOARD_ROUTE = DASHBOARD_SCREEN
    const val TRANSACTIONS_ROUTE = TRANSACTIONS_SCREEN
    const val SETTINGS_ROUTE = SETTINGS_SCREEN
    const val TRANSACTIONS_DETAILS_ROUTE = "$TRANSACTION_DETAILS_SCREEN/{$TRANSACTION_ID_ARG}"
}

class MainNavigationActions(private val navController: NavController) {

    fun bottomNavigationNavigateTo(destinationRoute: String) {
        navController.navigate(destinationRoute) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    fun navigateToTransactionDetails(transactionID: String) {
        navController.navigate("$TRANSACTION_DETAILS_SCREEN/$transactionID")
    }
}