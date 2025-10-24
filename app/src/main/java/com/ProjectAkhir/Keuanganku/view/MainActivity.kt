package com.ProjectAkhir.Keuanganku.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ProjectAkhir.Keuanganku.ui.theme.KeuangankuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeuangankuTheme {
                AppNavigation() // ✅ panggil navigasi utama
            }
        }
    }
}