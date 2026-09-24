package com.example.filfoodsapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Define the 4 tabs
enum class AppScreen(val title: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    Plans("Plans", Icons.Default.DateRange),
    About("About", Icons.Default.Info),
    Account("Account", Icons.Default.AccountCircle)
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(AppScreen.Home) }
    val FilBrand = Color(0xFF1A432B) // Your dark green

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
            ) {
                AppScreen.values().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = FilBrand,
                            selectedTextColor = FilBrand,
                            indicatorColor = Color(0xFFD4F0D6), // Mint background for selected item
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // This Surface pushes the content above the bottom bar
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = Color.White
        ) {
            // Swap screens based on the selected tab
            // Inside MainScreen.kt
            when (currentScreen) {
                AppScreen.Home -> HomeScreen()
                AppScreen.Plans -> PlansScreen()
                AppScreen.About -> AboutScreen()
                AppScreen.Account -> AccountScreen()
            }
        }
    }
}