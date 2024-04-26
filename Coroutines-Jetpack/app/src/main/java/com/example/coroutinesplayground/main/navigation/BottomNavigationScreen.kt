package com.example.coroutinesplayground.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.coroutinesplayground.R
import com.example.coroutinesplayground.main.navigation.MainNavigationDestinations.DASHBOARD_ROUTE
import com.example.coroutinesplayground.main.navigation.MainNavigationDestinations.SETTINGS_ROUTE
import com.example.coroutinesplayground.main.navigation.MainNavigationDestinations.TRANSACTIONS_ROUTE

sealed class BottomNavigationScreen (
    val route: String,
    val icon: ImageVector,
    val label: Int
) {
    companion object {
        val screens = listOf(Dashboard, Transactions, Settings)
    }

    object Dashboard : BottomNavigationScreen(DASHBOARD_ROUTE, Icons.Default.Home, R.string.screen_dashboard_label_text)
    object Transactions : BottomNavigationScreen(TRANSACTIONS_ROUTE, Icons.Default.List, R.string.screen_transactions_label_text)
    object Settings : BottomNavigationScreen(SETTINGS_ROUTE, Icons.Default.Settings, R.string.screen_settings_label_text)
}
