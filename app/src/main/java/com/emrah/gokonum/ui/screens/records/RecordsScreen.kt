package com.emrah.gokonum.ui.screens.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emrah.gokonum.data.database.AppDatabase
import com.emrah.gokonum.data.model.SavedLocation
import com.emrah.gokonum.ui.components.GlassCard
import com.emrah.gokonum.util.DistanceCalculator
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecordsScreen() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val locationDao = database.locationDao()

    var locations by remember { mutableStateOf<List<SavedLocation>>(emptyList()) }
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy HH:mm", Locale("tr", "TR")) }

    // Veritabanından verileri dinle
    LaunchedEffect(Unit) {
        locationDao.getAllLocations().collectLatest { list ->
            locations = list
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Başlık
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Kayıtlı Konumlar (${locations.size})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00E5FF),
                modifier = Modifier.padding(16.dp)
            )
        }

        if (locations.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz hiçbir konum kaydedilmedi.\n\nHaritada uzun basarak ekleyin.",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(locations) { location ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = location.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                                Text(
                                    text = "${"%.2f".format(location.latitude)}, ${"%.2f".format(location.longitude)}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF00E5FF)
                                )
                            }

                            if (location.note.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = location.note,
                                    fontSize = 14.sp,
                                    color = Color(0xFFBB86FC)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = dateFormat.format(location.date),
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}
