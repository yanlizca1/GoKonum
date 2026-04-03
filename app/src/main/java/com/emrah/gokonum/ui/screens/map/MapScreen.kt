package com.emrah.gokonum.ui.screens.map

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
import com.emrah.gokonum.ui.components.GlassCard
import com.emrah.gokonum.ui.theme.GoKonumTheme

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.0, 35.0), 8f) // Adana civarı başlangıç
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        // === ANA HARİTA ===
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                compassEnabled = true
            )
        )

        // === ÜST BAR (Glass efekti) ===
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
                Text(
                    text = "Adana",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // === NEON FLOATING ACTION BUTTON (Pin Ekle) ===
        FloatingActionButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF00E5FF),
            contentColor = Color.Black
        ) {
            Text(
                text = "+",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // === ALT BİLGİ PANELİ (Glass) ===
        GlassCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 80.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Konum eklemek için + butonuna bas",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        // Buraya sonra Bottom Sheet gelecek (pin ekleme popup)
        if (showBottomSheet) {
            // Basit placeholder - sonra geliştireceğiz
            AlertDialog(
                onDismissRequest = { showBottomSheet = false },
                title = { Text("Yeni Konum Ekle") },
                text = { Text("Bu özellik sonraki adımda eklenecek.") },
                confirmButton = {
                    Button(onClick = { showBottomSheet = false }) {
                        Text("Tamam")
                    }
                }
            )
        }
    }
}
