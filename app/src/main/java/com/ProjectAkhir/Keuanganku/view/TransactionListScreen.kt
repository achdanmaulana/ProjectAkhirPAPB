package com.ProjectAkhir.Keuanganku.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ProjectAkhir.Keuanganku.model.Transaction
import com.ProjectAkhir.Keuanganku.util.FirebaseHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(onAddTransactionClick: () -> Unit) {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    // Ambil data realtime dari Firebase
    LaunchedEffect(Unit) {
        FirebaseHelper.getTransactionsRealtime { list ->
            transactions = list
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Daftar Transaksi") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransactionClick) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = transaction.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rp ${transaction.amount}", style = MaterialTheme.typography.bodyMedium)
            Text(text = transaction.category, style = MaterialTheme.typography.bodySmall)
            Text(text = transaction.date, style = MaterialTheme.typography.bodySmall)
        }
    }
}