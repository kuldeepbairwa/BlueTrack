package com.kuldeep.bluetrack.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuldeep.bluetrack.ui.screens.AlertConfigScreen
import com.kuldeep.bluetrack.ui.screens.DeviceDetailScreen
import com.kuldeep.bluetrack.ui.screens.DeviceDiscoveryScreen
import com.kuldeep.bluetrack.ui.screens.SettingsScreen
import com.kuldeep.bluetrack.core.ui.theme.BlueTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueTrackTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BlueTrackApp()
                }
            }
        }
    }
}

@Composable
fun BlueTrackApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "deviceDiscovery") {
        composable("deviceDiscovery") { DeviceDiscoveryScreen(navController) }
        composable("deviceDetail/{deviceId}") { backStackEntry ->
            DeviceDetailScreen(
                deviceId = backStackEntry.arguments?.getString("deviceId") ?: "",
                navController = navController
            )
        }
        composable("settings") { SettingsScreen(navController) }
        composable("alertConfig") { AlertConfigScreen(navController) }
    }
}