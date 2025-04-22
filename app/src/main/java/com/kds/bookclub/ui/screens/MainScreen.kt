package com.kds.bookclub.ui.screens
//main screen of app
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.kds.bookclub.navigation.Routes
import com.kds.bookclub.ui.components.FancyBackButton
import com.kds.bookclub.ui.theme.PrimaryButtonDefaults
import com.kds.bookclub.ui.theme.elevatedShadow

@Composable
fun MainScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Reader"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Welcome Back, $userName!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        // browse button
        Button(
            onClick = { navController.navigate(Routes.SEARCH) },
            colors = PrimaryButtonDefaults(),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .elevatedShadow()
        ) {
            Text("Browse Books")
        }

        // reading list button
        Button(
            onClick = { navController.navigate(Routes.READING_LIST) },
            colors = PrimaryButtonDefaults(),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .elevatedShadow()
        ) {
            Text("Reading List")
        }

        // logout Button
        Button(
            onClick = { FirebaseAuth.getInstance().signOut(); navController.navigate(Routes.SIGN_IN) },
            colors = PrimaryButtonDefaults(),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .elevatedShadow()
        ) {
            Text("Logout")
        }
    }
}
