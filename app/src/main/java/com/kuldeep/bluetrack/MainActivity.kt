package com.kuldeep.bluetrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuldeep.bluetrack.ui.screens.AlertConfigScreen
import com.kuldeep.bluetrack.ui.screens.DeviceDetailScreen
import com.kuldeep.bluetrack.ui.screens.DeviceDiscoveryScreen
import com.kuldeep.bluetrack.ui.screens.SettingsScreen
import com.kuldeep.bluetrack.ui.theme.BlueTrackTheme

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