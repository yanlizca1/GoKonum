package com.emrah.gokonum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.emrah.gokonum.ui.screens.map.MapScreen
import com.emrah.gokonum.ui.theme.GoKonumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoKonumTheme {
                MapScreen()
            }
        }
    }
}
