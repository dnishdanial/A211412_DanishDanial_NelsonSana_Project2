package com.example.a211412_danishdanial_nelsonsana_project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a211412_danishdanial_nelsonsana_project2.navigation.KasihNavigation
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project2Theme {
                KasihNavigation()
            }
        }
    }
}
