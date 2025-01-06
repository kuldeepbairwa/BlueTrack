package com.kuldeep.bluetrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsGroup(title = "Alert Settings") {
                SliderSetting(
                    title = "Alert Distance Threshold",
                    value = 10f,
                    onValueChange = { /* Update threshold */ },
                    valueRange = 1f..50f,
                    steps = 49
                )
            }

            SettingsGroup(title = "Notification Preferences") {
                SwitchSetting(
                    title = "Sound",
                    checked = true,
                    onCheckedChange = { /* Update sound preference */ }
                )
                SwitchSetting(
                    title = "Vibration",
                    checked = true,
                    onCheckedChange = { /* Update vibration preference */ }
                )
                SwitchSetting(
                    title = "Visual",
                    checked = true,
                    onCheckedChange = { /* Update visual preference */ }
                )
            }

            SettingsGroup(title = "Scan Settings") {
                SliderSetting(
                    title = "Scan Duration",
                    value = 30f,
                    onValueChange = { /* Update scan duration */ },
                    valueRange = 5f..120f,
                    steps = 23
                )
            }

            SettingsGroup(title = "Appearance") {
                var isDarkMode by remember { mutableStateOf(false) }
                SwitchSetting(
                    title = "Dark Mode",
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
            }

            SettingsGroup(title = "Battery Optimization") {
                SwitchSetting(
                    title = "Enable Battery Optimization",
                    checked = true,
                    onCheckedChange = { /* Update battery optimization */ }
                )
            }
        }
    }
}

@Composable
fun SettingsGroup(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun SwitchSetting(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SliderSetting(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "${value.toInt()} ${if (title.contains("Distance")) "meters" else "seconds"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

