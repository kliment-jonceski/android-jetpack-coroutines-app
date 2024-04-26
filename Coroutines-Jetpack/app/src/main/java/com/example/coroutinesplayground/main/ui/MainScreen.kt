package com.example.coroutinesplayground.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coroutinesplayground.main.ui.dashboard.DashboardScreen
import com.example.coroutinesplayground.main.navigation.BottomNavigationScreen
import com.example.coroutinesplayground.main.navigation.MainNavigationActions
import com.example.coroutinesplayground.main.ui.settings.SettingsScreen
import com.example.coroutinesplayground.main.ui.transactions.TransactionsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(currentRoute: String,
               navigationActions: MainNavigationActions,
               content : @Composable () -> Unit) {
    //val navController = rememberNavController()

    Scaffold (
        topBar = {},
        //bottomBar = { BottomNavigationBar(navController = navController) },
        bottomBar = { BottomNavigationBar(currentRoute = currentRoute, navigationActions = navigationActions)},
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                //NavigationHost(navController = navController)
                //MainNavHost()
                content()
            }
        },
    )
}


//@Composable
//fun NavigationHost(navController: NavHostController) {
//    NavHost(navController,
//        startDestination = BottomNavigationScreen.Dashboard.route) {
//        composable(BottomNavigationScreen.Dashboard.route) { DashboardScreen() }
//        composable(BottomNavigationScreen.Transactions.route) { TransactionsScreen() }
//        composable(BottomNavigationScreen.Settings.route) { SettingsScreen() }
//    }
//}

@Composable
fun BottomNavigationBar(currentRoute: String,
                        navigationActions: MainNavigationActions) {

    BottomNavigation {
        //val navBackStackEntry by navController.currentBackStackEntryAsState()
        //val currentRoute = navBackStackEntry?.destination?.route


        BottomNavigationScreen.screens.forEach { screenItem ->
            BottomNavigationItem(
                selected = currentRoute == screenItem.route,
                onClick = {
                    navigationActions.bottomNavigationNavigateTo(screenItem.route)
//                    navController.navigate(screenItem.route) {
//                        popUpTo(navController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
                          },
                icon = { Icon(screenItem.icon, contentDescription = null) },
                modifier = Modifier.padding(8.dp),
                label = { Text (
                    text =  stringResource(id = screenItem.label),
                    fontSize = 14.sp
                ) },
                alwaysShowLabel = false
            )
        }
    }
}