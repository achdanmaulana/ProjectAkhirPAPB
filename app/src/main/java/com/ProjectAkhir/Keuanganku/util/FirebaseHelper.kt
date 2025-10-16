package com.ProjectAkhir.Keuanganku.util

import android.util.Log
import com.ProjectAkhir.Keuanganku.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("transactions")

    fun getTransactions(onResult: (List<Transaction>) -> Unit) {
        collection.get()
            .addOnSuccessListener { result ->
                val list = result.toObjects(Transaction::class.java)
                onResult(list)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "❌ Error fetching data", e)
                onResult(emptyList())
            }
    }

    fun addTransaction(transaction: Transaction, onComplete: (Boolean) -> Unit) {
        collection.add(transaction)
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "✅ Transaction added: ${transaction.title}")
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "❌ Error adding transaction", e)
                onComplete(false)
            }
    }
}