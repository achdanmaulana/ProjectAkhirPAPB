package com.ProjectAkhir.Keuanganku.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ProjectAkhir.Keuanganku.navigation.NavGraph
import com.ProjectAkhir.Keuanganku.ui.theme.KeuangankuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeuangankuTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}