package com.ProjectAkhir.Keuanganku.model

data class Transaction(
    val title: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val date: String = ""
)