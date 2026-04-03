package com.emrah.gokonum.ui.screens.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emrah.gokonum.data.model.SavedLocation
import com.emrah.gokonum.ui.components.GlassCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecordsScreen(
    locations: List<SavedLocation> = emptyList(),   // sonra veritabanından gelecek
    onLocationClick: (SavedLocation) -> Unit = {}
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Üst Başlık
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Kayıtlı Konumlar",
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
                    text = "Henüz konum kaydedilmedi\n+ butonundan ekleyebilirsiniz",
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
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = location.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Lat: ${"%.5f".format(location.latitude)}   Lon: ${"%.5f".format(location.longitude)}",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            if (location.note.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = location.note,
                                    fontSize = 14.sp,
                                    color = Color(0xFFBB86FC)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = dateFormat.format(location.date),
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}
