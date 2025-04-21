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

    // Derived State
    val filteredExercises = remember(exercises, searchQuery, selectedCategory, selectedMuscle) {
        exercises.filter { exercise ->
            (searchQuery.isEmpty() || exercise.name?.contains(searchQuery, true) == true) &&
                    (selectedCategory == null || exercise.category == selectedCategory!!.id) &&
                    (selectedMuscle == null || selectedMuscle!!.id in exercise.muscles)
        }
    }

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
            isLoading -> FullScreenLoader()
            error != null -> ErrorMessage(error!!)
            else -> {
                SearchBar(searchQuery) { searchQuery = it }

                // Add this debug preview
                ExerciseDebugPreview(filteredExercises)

                CategoryFilterRow(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                MuscleFilterRow(
                    muscles = muscles,
                    selectedMuscle = selectedMuscle,
                    onMuscleSelected = { selectedMuscle = it }
                )

                ExerciseList(
                    exercises = filteredExercises,
                    allExercises = exercises,
                    categories = categories,
                    muscles = muscles
                )
            }
        }
    }
}



// Componentized Sub-Composables

@Composable
private fun FullScreenLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun SearchBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search exercises...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
    )
}

@Composable
private fun CategoryFilterRow(
    categories: List<ExerciseCategory>,
    selectedCategory: ExerciseCategory?,
    onCategorySelected: (ExerciseCategory?) -> Unit
) {
    if (categories.isEmpty()) return

    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory != null,
                onClick = { onCategorySelected(null) },
                label = { Text("All Categories") }
            )
        }
        items(categories) { category ->
            FilterChip(
                selected = selectedCategory?.id == category.id,
                onClick = { onCategorySelected(category) },
                label = { Text(category.name ?: "Unnamed Category") }
            )
        }
    }
}

@Composable
private fun MuscleFilterRow(
    muscles: List<Muscle>,
    selectedMuscle: Muscle?,
    onMuscleSelected: (Muscle?) -> Unit
) {
    if (muscles.isEmpty()) return

    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedMuscle != null,
                onClick = { onMuscleSelected(null) },
                label = { Text("All Muscles") }
            )
        }
        items(muscles) { muscle ->
            FilterChip(
                selected = selectedMuscle?.id == muscle.id,
                onClick = { onMuscleSelected(muscle) },
                label = { Text(muscle.name ?: "Unnamed Muscle") }
            )
        }
    }
}

@Composable
private fun ExerciseDebugPreview(exercises: List<Exercise>) {
    if (exercises.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "DEBUG PREVIEW (First 3)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                exercises.take(3).forEach { ex ->
                    Text(
                        text = ex.debugString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseList(
    exercises: List<Exercise>,
    allExercises: List<Exercise>,
    categories: List<ExerciseCategory>,
    muscles: List<Muscle>
) {
    LazyColumn(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxSize()
    ) {
        if (exercises.isEmpty()) {
            item {
                Text(
                    text = if (allExercises.isEmpty()) "No exercises loaded"
                    else "No matching exercises found",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(
                items = exercises,
                key = { exercise -> exercise.id ?: -1 } // Ensure unique keys
            ) { exercise ->
                ExpandableExerciseCard(
                    exercise = exercise,
                    categoryName = categories.find { it.id == exercise.category }?.name ?: "Unknown",
                    muscleNames = exercise.muscles.mapNotNull { muscleId ->
                        muscles.find { it.id == muscleId }?.name
                    }
                )
            }
        }
    }
}

@Composable
private fun ExpandableExerciseCard(
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.displayName(),
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
                    text = exercise.description ?: "No description available",
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