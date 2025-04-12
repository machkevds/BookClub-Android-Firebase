package com.kds.moveamenable.ui.screens
//home screen with navigation buttons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onWorkoutClick: () -> Unit,
    onFocusClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    //layout, column, buttons onclick listeners, etc
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onWorkoutClick) {
            Text("My Workouts")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onFocusClick) {
            Text("Focus Tools")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onInfoClick) {
            Text("Exercise Info")
        }
    }
}