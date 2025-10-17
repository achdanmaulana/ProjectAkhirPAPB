package com.ProjectAkhir.Keuanganku.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ProjectAkhir.Keuanganku.ui.theme.KeuangankuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeuangankuTheme {
                AppNavigation()
            }
        }
    }
}