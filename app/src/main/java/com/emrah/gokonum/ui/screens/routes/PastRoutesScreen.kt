package com.emrah.gokonum.ui.screens.routes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emrah.gokonum.data.database.AppDatabase
import com.emrah.gokonum.data.model.SavedRoute
import com.emrah.gokonum.ui.components.GlassCard
import com.emrah.gokonum.util.GPXExporter
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PastRoutesScreen() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val routeDao = database.routeDao()

    var routes by remember { mutableStateOf<List<SavedRoute>>(emptyList()) }

    LaunchedEffect(Unit) {
        routeDao.getAllRoutes().collectLatest { list ->
            routes = list
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Geçmiş Rotalar (${routes.size})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00E5FF),
                modifier = Modifier.padding(16.dp)
            )
        }

        if (routes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz kaydedilmiş rota yok.\n\nRota kaydetme özelliği yakında eklenecek.",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(routes) { route ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = route.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${"%.1f".format(route.totalDistance)} km • ${route.duration}",
                                color = Color(0xFF00E5FF),
                                fontSize = 15.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { /* Haritada göster (ileride) */ },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFBB86FC))
                                ) {
                                    Text("Göster")
                                }
                                Button(
                                    onClick = { GPXExporter.exportRouteToGPX(context, route) },
                                    colors = ButtonDefaults.buttonColors(Color(0xFF00E5FF)),
                                    contentColor = Color.Black
                                ) {
                                    Text("GPX İndir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
