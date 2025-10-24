package com.ProjectAkhir.Keuanganku.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ProjectAkhir.Keuanganku.model.Transaction
import com.ProjectAkhir.Keuanganku.util.FirebaseHelper
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    onAddTransactionClick: () -> Unit
) {
    val firebaseHelper = remember { FirebaseHelper() }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    LaunchedEffect(Unit) {
        firebaseHelper.getTransactionsRealtime { list ->
            transactions = list
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Daftar Transaksi") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransactionClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Transaksi")
            }
        }
    ) { padding ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada transaksi.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions) { transaction ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = transaction.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Rp ${transaction.amount}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = transaction.category, style = MaterialTheme.typography.bodySmall)
                            Text(text = transaction.date, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}