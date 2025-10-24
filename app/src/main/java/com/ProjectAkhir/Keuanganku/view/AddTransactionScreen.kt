package com.ProjectAkhir.Keuanganku.view

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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
    val scrollState = rememberScrollState()

    // 🔹 Fungsi format Rupiah (tanpa simbol "Rp" agar saat ketik tetap lancar)
    fun formatCurrency(input: String): String {
        val clean = input.replace("[^\\d]".toRegex(), "")
        if (clean.isEmpty()) return ""

        val parsed = clean.toDouble()
        val symbols = DecimalFormatSymbols(Locale("id", "ID"))
        val decimalFormat = DecimalFormat("#,###", symbols)
        return decimalFormat.format(parsed)
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
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔸 Nama transaksi
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nama Transaksi") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Input jumlah uang dengan format otomatis
            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    val cleanString = newValue.replace("[^\\d]".toRegex(), "")
                    if (cleanString.isNotEmpty()) {
                        amount = formatCurrency(cleanString)
                    } else {
                        amount = ""
                    }
                },
                label = { Text("Jumlah Uang (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Pilih tanggal transaksi
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

            // 🔸 Tombol Simpan
            Button(
                onClick = {
                    val numericValue = amount.replace("[^\\d]".toRegex(), "").toDoubleOrNull() ?: 0.0
                    val transaction = Transaction(
                        title = title,
                        amount = numericValue,
                        date = if (date.isNotEmpty()) date else "Belum dipilih"
                    )

                    firebaseHelper.addTransaction(transaction) { success ->
                        if (success) {
                            Toast.makeText(context, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show()
                            title = ""
                            amount = ""
                            date = ""
                            onTransactionAdded()
                        } else {
                            Toast.makeText(context, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && amount.isNotBlank() && date.isNotBlank()
            ) {
                Text("Simpan Transaksi")
            }
        }
    }
}