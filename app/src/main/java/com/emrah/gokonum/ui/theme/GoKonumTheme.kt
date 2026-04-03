
package com.emrah.gokonum.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),      // Neon Turkuaz
    secondary = Color(0xFFBB86FC),    // Neon Mor
    background = Color(0xFF050505),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onSurface = Color.White
)

@Composable
fun GoKonumTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
