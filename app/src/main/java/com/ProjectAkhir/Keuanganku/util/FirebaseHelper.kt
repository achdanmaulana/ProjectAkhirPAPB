package com.ProjectAkhir.Keuanganku.util

import com.google.firebase.firestore.FirebaseFirestore
import com.ProjectAkhir.Keuanganku.model.Transaction

class FirebaseHelper {

    private val firestore = FirebaseFirestore.getInstance()

    fun addTransaction(transaction: Transaction, onComplete: (Boolean) -> Unit) {
        firestore.collection("transactions")
            .add(transaction)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getTransactionsRealtime(onResult: (List<Transaction>) -> Unit) {
        firestore.collection("transactions")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                val list = snapshot.toObjects(Transaction::class.java)
                onResult(list)
            }
    }
}