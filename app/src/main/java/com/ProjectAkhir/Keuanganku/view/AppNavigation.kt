package com.ProjectAkhir.Keuanganku.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "transaction_list") {
        composable("transaction_list") {
            TransactionListScreen(
                onAddClick = {
                    navController.navigate("add_transaction")
                }
            )
        }

        composable("add_transaction") {
            AddTransactionScreen(
                onTransactionAdded = {
                    navController.popBackStack()
                }
            )
        }
    }
}