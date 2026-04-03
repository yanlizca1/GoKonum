package com.emrah.gokonum.ui.screens.map

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.emrah.gokonum.data.database.AppDatabase
import com.emrah.gokonum.data.model.SavedLocation
import com.emrah.gokonum.ui.components.GlassCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.0, 35.3), 10f) // Adana civarı
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var clickedLatLng by remember { mutableStateOf<LatLng?>(null) }

    val database = remember { AppDatabase.getDatabase(context) }
    val locationDao = database.locationDao()

    // Veritabanından kaydedilen konumları al (şimdilik basit liste)
    var savedLocations by remember { mutableStateOf<List<SavedLocation>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Gerçekte Flow ile dinleyeceğiz, şimdilik basit
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = { latLng ->
                clickedLatLng = latLng
                showAddDialog = true
            },
            uiSettings = MapUiSettings(zoomControlsEnabled = true)
        ) {
            // Kaydedilen konumları haritada göster (pin)
            savedLocations.forEach { loc ->
                Marker(
                    state = MarkerState(position = LatLng(loc.latitude, loc.longitude)),
                    title = loc.name,
                    snippet = loc.note
                )
            }
        }

        // Üst Glass Bar
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GoKonum",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00E5FF)
                )
            }
        }

        // Neon Floating Button
        FloatingActionButton(
            onClick = { /* İleride başka işlev */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF00E5FF),
            contentColor = Color.Black
        ) {
            Text(text = "+", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }

        // Alt Glass Panel
        GlassCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 80.dp)
        ) {
            Text(
                text = "Haritada uzun basın → Yeni konum ekleyin",
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Pin Ekleme Dialog
        if (showAddDialog && clickedLatLng != null) {
            var locationName by remember { mutableStateOf("") }
            var note by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Yeni Konum Ekle", color = Color(0xFF00E5FF)) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = locationName,
                            onValueChange = { locationName = it },
                            label = { Text("Konum Adı") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("Not (opsiyonel)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (locationName.isNotBlank()) {
                                val newLocation = SavedLocation(
                                    name = locationName,
                                    latitude = clickedLatLng!!.latitude,
                                    longitude = clickedLatLng!!.longitude,
                                    note = note,
                                    date = Date()
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    locationDao.insertLocation(newLocation)
                                }

                                Toast.makeText(context, "$locationName kaydedildi!", Toast.LENGTH_SHORT).show()
                                showAddDialog = false
                                locationName = ""
                                note = ""
                            }
                        }
                    ) {
                        Text("Kaydet")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("İptal")
                    }
                }
            )
        }
    }
}
