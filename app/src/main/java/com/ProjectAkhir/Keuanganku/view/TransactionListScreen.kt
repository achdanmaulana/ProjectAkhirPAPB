package com.ProjectAkhir.Keuanganku.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ProjectAkhir.Keuanganku.model.Transaction
import com.ProjectAkhir.Keuanganku.util.FirebaseHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen() {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    LaunchedEffect(Unit) {
        FirebaseHelper.getTransactions { list ->
            transactions = list
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📊 Daftar Transaksi") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* nanti ke AddTransactionScreen */ }) {
                Text("+")
            }
        }
    ) { padding ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada transaksi.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(transactions) { trx ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(trx.title, style = MaterialTheme.typography.titleMedium)
                            Text("Rp ${trx.amount}", style = MaterialTheme.typography.bodyMedium)
                            Text(trx.date, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}