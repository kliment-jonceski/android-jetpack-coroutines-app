package com.example.coroutinesplayground.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coroutinesplayground.main.navigation.MainDestinationsArgs.TRANSACTION_ID_ARG
import com.example.coroutinesplayground.main.ui.MainScreen
import com.example.coroutinesplayground.main.ui.dashboard.DashboardScreen
import com.example.coroutinesplayground.main.ui.dashboard.DashboardViewModel
import com.example.coroutinesplayground.main.ui.settings.SettingsScreen
import com.example.coroutinesplayground.main.ui.transaction_details.TransactionDetailsScreen
import com.example.coroutinesplayground.main.ui.transactions.TransactionsScreen
import com.example.coroutinesplayground.main.ui.transactions.TransactionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigationDestinations.MAIN_ROUTE,
    navActions: MainNavigationActions = remember(navController) {
        MainNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    
    NavHost(navController = navController, startDestination = startDestination) {
        composable(MainNavigationDestinations.MAIN_ROUTE) {
            val bottomNavController: NavHostController = rememberNavController()
            val bottomNavActions = remember(bottomNavController) {
                MainNavigationActions(bottomNavController)
            }
            val bottomCurrentNavBackStackEntry by navController.currentBackStackEntryAsState()
            val bottomCurrentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
            MainScreen(
            currentRoute = bottomCurrentRoute,
            navigationActions = bottomNavActions
        ) { BottomNavHost(bottomNavController = bottomNavController, navActions) }
        }
        composable(MainNavigationDestinations.TRANSACTIONS_DETAILS_ROUTE) {
            TransactionDetailsScreen(it.arguments?.getString(TRANSACTION_ID_ARG))
        }
    }
}

@Composable
fun BottomNavHost (
    bottomNavController: NavHostController,
    navActions: MainNavigationActions,
    startDestination: String = MainNavigationDestinations.DASHBOARD_ROUTE,
) {
    //val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    //val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    val transactionsViewModel : TransactionsViewModel = koinViewModel()
    val dashboardViewModel: DashboardViewModel = koinViewModel()

    NavHost(
        navController = bottomNavController,
        startDestination = startDestination
    ) {
        composable(MainNavigationDestinations.DASHBOARD_ROUTE) {
            DashboardScreen(
                dashboardViewModel.balanceState.collectAsState(),
                dashboardViewModel.latestTransactionsState.collectAsState()
            )
        }
        composable(MainNavigationDestinations.MAIN_ROUTE) {
            DashboardScreen(
                dashboardViewModel.balanceState.collectAsState(),
                dashboardViewModel.latestTransactionsState.collectAsState()
            )
        }
        composable(MainNavigationDestinations.TRANSACTIONS_ROUTE) {
            TransactionsScreen(
                navActions,
                transactionsViewModel.transactionUIState.collectAsState()
            )
        }
        composable(MainNavigationDestinations.SETTINGS_ROUTE) { SettingsScreen() }
    }
}