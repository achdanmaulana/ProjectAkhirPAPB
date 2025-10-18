package com.ProjectAkhir.Keuanganku.view

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ProjectAkhir.Keuanganku.model.Transaction
import com.ProjectAkhir.Keuanganku.util.FirebaseHelper
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onTransactionAdded: () -> Unit
) {
    val context = LocalContext.current
    val firebaseHelper = remember { FirebaseHelper() }

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // 🔹 Format angka otomatis jadi rupiah saat input
    fun formatCurrency(input: String): String {
        return try {
            val cleanString = input.replace("[Rp,.\\s]".toRegex(), "")
            if (cleanString.isEmpty()) return ""
            val parsed = cleanString.toDouble()
            "Rp" + NumberFormat.getNumberInstance(Locale("id", "ID")).format(parsed)
        } catch (e: Exception) {
            input
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Transaksi") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔸 Nama transaksi
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nama Transaksi") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Input uang dengan format
            TextField(
                value = amount,
                onValueChange = { newValue ->
                    amount = formatCurrency(newValue)
                },
                label = { Text("Jumlah Uang") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Tanggal pakai DatePicker
            Button(
                onClick = {
                    val datePicker = DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            calendar.set(year, month, day)
                            date = formatter.format(calendar.time)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (date.isEmpty()) "Pilih Tanggal" else "Tanggal: $date")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔸 Tombol simpan
            Button(
                onClick = {
                    val amountValue = amount.replace("[Rp,.\\s]".toRegex(), "").toDoubleOrNull() ?: 0.0
                    val transaction = Transaction(
                        title = title,
                        amount = amountValue,
                        date = if (date.isNotEmpty()) date else "Belum dipilih"
                    )

                    firebaseHelper.addTransaction(transaction) { success ->
                        if (success) {
                            Toast.makeText(context, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show()
                            onTransactionAdded()
                        } else {
                            Toast.makeText(context, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && amount.isNotBlank()
            ) {
                Text("Simpan Transaksi")
            }
        }
    }
}