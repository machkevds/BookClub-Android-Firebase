package com.kds.moveamenable.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kds.moveamenable.navigation.Routes

data class WorkoutLog(
    val id: Int,
    val name: String,
    val date: String,
    val notesPreview: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorkoutScreen(navController: NavController) {
    val workoutLogs = remember {
        mutableStateListOf(
            WorkoutLog(1, "Morning Routine", "2023-11-15", "Push-ups, Squats, Plank"),
            WorkoutLog(2, "Evening Yoga", "2023-11-14", "Sun Salutation x5"),
            WorkoutLog(3, "Cardio Blast", "2023-11-13", "Running, Jump rope")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Workouts") },

                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.MAIN_SCREEN) }) {
                        Icon(Icons.Default.ArrowBackIosNew  , contentDescription = "Back to Main Screen")
                    }
                },

                actions = {
                    IconButton(onClick = { /* TODO: Delete selected */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add new workout */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(workoutLogs) { log ->
                WorkoutLogCard(log = log)
            }
        }
    }
}

@Composable
fun WorkoutLogCard(log: WorkoutLog) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = log.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = log.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = log.notesPreview,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}