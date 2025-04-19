package com.kds.moveamenable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kds.moveamenable.models.Exercise
import com.kds.moveamenable.navigation.Routes
import com.kds.moveamenable.viewmodels.ExerciseViewModel
import com.kds.moveamenable.models.ExerciseCategory
import com.kds.moveamenable.models.Muscle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavController) {
    val viewModel: ExerciseViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ExerciseCategory?>(null) }
    var selectedMuscle by remember { mutableStateOf<Muscle?>(null) }

    val categories by viewModel.categories.collectAsState()
    val muscles by viewModel.muscles.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Exercise Information") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.MAIN_SCREEN) }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            }
        )

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            error != null -> {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search exercises...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )

                // empty state check for new categories
                if (categories.isEmpty()) {
                    Text(
                        "No categories loaded",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedCategory != null,
                                onClick = { selectedCategory = null },
                                label = { Text("All Categories") }
                            )
                        }
                        items(categories) { category ->
                            FilterChip(
                                selected = selectedCategory?.id == category.id,
                                onClick = { selectedCategory = category },
                                label = { Text(category.name ?: "Unnamed Category") } // Null-safe
                            )
                        }
                    }
                }

                // empty state check for muscles
                if (muscles.isEmpty()) {
                    Text(
                        "No muscles loaded",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedMuscle != null,
                                onClick = { selectedMuscle = null },
                                label = { Text("All Muscles") }
                            )
                        }
                        items(muscles) { muscle ->
                            FilterChip(
                                selected = selectedMuscle?.id == muscle.id,
                                onClick = { selectedMuscle = muscle },
                                label = { Text(muscle.name ?: "Unnamed Muscle") } // Null-safe
                            )
                        }
                    }
                }

                // main exercise list w/ empty state
                val filteredExercises = remember(searchQuery, selectedCategory, selectedMuscle) {
                    exercises.filter { exercise ->
                        (searchQuery.isEmpty() ||
                                exercise.name?.contains(searchQuery, true) == true) && // Null-safe
                                (selectedCategory == null || exercise.category == selectedCategory!!.id) &&
                                (selectedMuscle == null || selectedMuscle!!.id in exercise.muscles)
                    }
                }

                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    if (filteredExercises.isEmpty()) {
                        item {
                            Text(
                                text = if (exercises.isEmpty()) "No exercises loaded"
                                else "No matching exercises found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    items(filteredExercises) { exercise ->
                        ExpandableExerciseCard(
                            exercise = exercise,
                            categoryName = categories.find { it.id == exercise.category }?.name
                                ?: "Unknown",
                            muscleNames = exercise.muscles.mapNotNull { muscleId ->
                                muscles.find { it.id == muscleId }?.name
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableExerciseCard(
    exercise: Exercise,
    categoryName: String?,
    muscleNames: List<String?>
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.name ?: "Unnamed Exercise", // [NEW] Null-safe
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Category: ${categoryName ?: "Unknown"}")
                Text("Muscles: ${muscleNames.filterNotNull().joinToString()}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = exercise.description ?: "No description available", // [NEW] Null-safe
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add to Workout")
                }
            }
        }
    }
}