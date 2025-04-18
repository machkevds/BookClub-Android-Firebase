package com.kds.moveamenable.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon


import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kds.moveamenable.navigation.Routes

data class Exercise(
    val id: Int,
    val name: String,
    val muscleGroup: String,
    val description: String,
    val instructions: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavController) {
    val exercises = remember {
        mutableStateListOf(
            Exercise(1, "Push-up", "Upper", "Bodyweight exercise", "1. Start in plank position..."),
            Exercise(2, "Squat", "Lower", "Leg exercise", "1. Stand with feet shoulder-width apart..."),
            Exercise(3, "Pull-up", "Back", "Upper body exercise", "1. Grab the bar with palms facing away...")
        )
    }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text("Exercise Information") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.MAIN_SCREEN) }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back to Main Screen")
                }
            }
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search by muscle group: upper, lower, back, or specific muscle") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(exercises.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.muscleGroup.contains(searchQuery, ignoreCase = true)
            }) { exercise ->
                ExpandableExerciseCard(exercise = exercise)
            }
        }
    }
}

@Composable
fun ExpandableExerciseCard(exercise: Exercise) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Muscle Group: ${exercise.muscleGroup}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = exercise.instructions,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}