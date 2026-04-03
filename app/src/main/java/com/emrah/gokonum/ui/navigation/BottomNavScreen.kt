package com.emrah.gokonum.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emrah.gokonum.ui.screens.map.MapScreen
import com.emrah.gokonum.ui.screens.records.RecordsScreen
import com.emrah.gokonum.ui.screens.routes.PastRoutesScreen

@Composable
fun BottomNavScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Harita", "Kayıtlar", "Rotalar")

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0A0A0A),
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(
                                text = when (index) {
                                    0 -> "🗺️"
                                    1 -> "📍"
                                    2 -> "🛣️"
                                    else -> "⚙️"
                                },
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF00E5FF),
                            selectedTextColor = Color(0xFF00E5FF),
                            unselectedIconColor = Color.White.copy(alpha = 0.6f),
                            unselectedTextColor = Color.White.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> MapScreen()
                1 -> RecordsScreen()
                2 -> PastRoutesScreen()
            }
        }
    }
}
