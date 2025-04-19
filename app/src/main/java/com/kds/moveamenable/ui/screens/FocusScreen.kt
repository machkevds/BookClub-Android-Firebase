package com.kds.moveamenable.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kds.moveamenable.navigation.Routes


@Suppress("UNUSED_PARAMETER")
//todo
//Keep navController if you might need to:
//Navigate to other screens (e.g., settings, stats, or different focus modes)
//Button(onClick = { navController.navigate("settings") }) { ... }

//Pass data to other screens (e.g., completed focus sessions)
//navController.navigate("stats/${completedMinutes}")

//Handle back navigation programmatically
//IconButton(onClick = { navController.popBackStack() }) { ... }
@Composable
fun FocusScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        IconButton(
            onClick = { navController.navigate(Routes.MAIN_SCREEN) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back to Main Screen"
            )
        }

        // timer
        Text(
            text = "Focus Time",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var timerRunning by remember { mutableStateOf(false) }
        var timerSeconds by remember { mutableStateOf(1500) } // 25 minutes

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatTime(timerSeconds),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = { timerRunning = !timerRunning }) {
                    Icon(
                        if (timerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (timerRunning) "Pause" else "Start"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(if (timerRunning) "Pause" else "Start")
                }

                Button(onClick = { timerSeconds = 1500; timerRunning = false }) {
                    Icon(Icons.Default.Replay, contentDescription = "Reset")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text("Reset")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // sounds
        Text(
            text = "Focus Sounds",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val sounds = listOf(
            "White Noise" to false,
            "Nature Sounds" to false,
            "Binaural Beats" to false
        )

        sounds.forEach { (sound, _) ->
            var isPlaying by remember { mutableStateOf(false) }
            var isLooping by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sound,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = { isPlaying = !isPlaying }) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                    }

                    IconButton(onClick = { isLooping = !isLooping }) {
                        Icon(
                            Icons.Default.Loop,
                            contentDescription = "Loop",
                            tint = if (isLooping) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}