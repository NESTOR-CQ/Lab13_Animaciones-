package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.lab13_animaciones.ui.screens.Ejercicio1Screen
import com.example.lab13_animaciones.ui.screens.Ejercicio2Screen
import com.example.lab13_animaciones.ui.screens.Ejercicio3Screen
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13_AnimacionesTheme {
                var selectedTab by remember { mutableIntStateOf(2) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                label = { Text("Ejercicio 1") },
                                icon = { Text("👁️") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                label = { Text("Ejercicio 2") },
                                icon = { Text("🎨") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 2,
                                onClick = { selectedTab = 2 },
                                label = { Text("Ejercicio 3") },
                                icon = { Text("📐") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        when (selectedTab) {
                            0 -> Ejercicio1Screen()
                            1 -> Ejercicio2Screen()
                            2 -> Ejercicio3Screen()
                        }
                    }
                }
            }
        }
    }
}
