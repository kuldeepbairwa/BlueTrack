package com.kuldeep.bluetrack.ui.screens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDiscoveryScreen(navController: NavController) {
    var isScanning by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var devices by remember { mutableStateOf(emptyList<BluetoothDevice>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BlueTrack") },
                actions = {
                    IconButton(onClick = { /* Open search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isScanning = !isScanning },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    if (isScanning) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = if (isScanning) "Stop Scan" else "Start Scan"
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimatedVisibility(
                visible = isScanning,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(devices) { device ->
                    DeviceCard(device = device, onDeviceClick = { navController.navigate("deviceDetail/${device.id}") })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(device: BluetoothDevice, onDeviceClick: () -> Unit) {
    Card(
        onClick = onDeviceClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            SignalStrengthIndicator(rssi = device.rssi)
            Spacer(modifier = Modifier.width(16.dp))
            if (device.isFavorite) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SignalStrengthIndicator(rssi: Int) {
    val strength = when {
        rssi > -50 -> 3
        rssi > -60 -> 2
        rssi > -70 -> 1
        else -> 0
    }

    Row {
        repeat(3) { index ->
            Icon(
                Icons.Default.SignalCellularAlt1Bar,
                contentDescription = null,
                tint = if (index < strength) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

data class BluetoothDevice(
    val id: String,
    val name: String,
    val address: String,
    val rssi: Int,
    val isFavorite: Boolean
)

