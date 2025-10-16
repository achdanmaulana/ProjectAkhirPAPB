package com.ProjectAkhir.Keuanganku.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ProjectAkhir.Keuanganku.view.AddTransactionScreen
import com.ProjectAkhir.Keuanganku.view.TransactionListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "transaction_list"
    ) {
        composable("transaction_list") {
            TransactionListScreen(
                onAddTransactionClick = { navController.navigate("add_transaction") }
            )
        }
        composable("add_transaction") {
            AddTransactionScreen(
                onTransactionAdded = { navController.popBackStack() }
            )
        }
    }
}