package com.ProjectAkhir.Keuanganku.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ProjectAkhir.Keuanganku.model.Transaction

object FirebaseHelper { // pakai object biar singleton (cukup 1 instance)

    private val firestore = FirebaseFirestore.getInstance()
    private const val COLLECTION_NAME = "transactions"

    // 🔥 Tambah transaksi baru
    fun addTransaction(transaction: Transaction, onComplete: (Boolean) -> Unit) {
        firestore.collection(COLLECTION_NAME)
            .add(transaction)
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "✅ Berhasil menambahkan transaksi: ${transaction.title}")
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "❌ Gagal menambahkan transaksi", e)
                onComplete(false)
            }
    }

    // 👀 Ambil data transaksi realtime
    fun getTransactionsRealtime(onResult: (List<Transaction>) -> Unit) {
        firestore.collection(COLLECTION_NAME)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    Log.e("FirebaseHelper", "❌ Error ambil data transaksi", error)
                    onResult(emptyList())
                    return@addSnapshotListener
                }

                val list = snapshot.toObjects(Transaction::class.java)
                Log.d("FirebaseHelper", "📦 Dapat ${list.size} transaksi dari Firestore")
                onResult(list)
            }
    }
}