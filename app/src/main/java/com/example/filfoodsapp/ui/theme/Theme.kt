package com.example.filfoodsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBrand,
    secondary = SecondaryBrand,
    background = BackgroundMint,
    surface = CardSurface,
    onPrimary = CardSurface,
    onBackground = PrimaryText,
    onSurface = PrimaryText
)

@Composable
fun FILFoodsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}