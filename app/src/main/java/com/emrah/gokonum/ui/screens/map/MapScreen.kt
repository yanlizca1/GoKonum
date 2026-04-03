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
        position = CameraPosition.fromLatLngZoom(LatLng(37.0, 35.3), 10f)
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var clickedLatLng by remember { mutableStateOf<LatLng?>(null) }

    val database = remember { AppDatabase.getDatabase(context) }
    val locationDao = database.locationDao()

    var savedLocations by remember { mutableStateOf<List<SavedLocation>>(emptyList()) }

    // Veritabanını dinle ve haritada pinleri göster
    LaunchedEffect(Unit) {
        locationDao.getAllLocations().collect { list ->
            savedLocations = list
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = { latLng ->
                clickedLatLng = latLng
                showAddDialog = true
            },
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                compassEnabled = true
            )
        ) {
            // Kaydedilen tüm konumları haritada göster
            savedLocations.forEach { loc ->
                Marker(
                    state = MarkerState(position = LatLng(loc.latitude, loc.longitude)),
                    title = loc.name,
                    snippet = if (loc.note.isNotEmpty()) loc.note else null,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                )
            }
        }

        // Neon Üst Bar
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "GoKonum",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00E5FF)
                )
                Text(
                    text = "Adana",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // Neon FAB
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF00E5FF),
            contentColor = Color.Black
        ) {
            Text("+", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        }

        // Alt Bilgi
        GlassCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 90.dp)
        ) {
            Text(
                text = "Haritada uzun basın → Konum ekleyin",
                color = Color.White.copy(alpha = 0.75f),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Yeni Konum Ekleme Dialog
        if (showAddDialog && clickedLatLng != null) {
            var name by remember { mutableStateOf("") }
            var note by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Yeni Konum Ekle", color = Color(0xFF00E5FF)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Konum Adı *") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("Not (isteğe bağlı)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (name.isNotBlank()) {
                                val newLoc = SavedLocation(
                                    name = name,
                                    latitude = clickedLatLng!!.latitude,
                                    longitude = clickedLatLng!!.longitude,
                                    note = note,
                                    date = Date()
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    locationDao.insertLocation(newLoc)
                                }

                                Toast.makeText(context, "$name kaydedildi", Toast.LENGTH_SHORT).show()
                                showAddDialog = false
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
