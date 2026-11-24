package com.example.practica_1_segundo_parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.practica_1_segundo_parcial.ui.disney.DisneyScreen
import com.example.practica_1_segundo_parcial.ui.theme.Practica_1_Segundo_ParcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica_1_Segundo_ParcialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisneyScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
