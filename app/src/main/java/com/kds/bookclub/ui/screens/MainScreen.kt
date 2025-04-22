package com.kds.bookclub.ui.screens
//main screen of app
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            style = MaterialTheme.typography.headlineMedium.copy(
                //added some effects to welcome text
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // subtitle to motivate reading
        Text(
            text = "\"Between the pages of a book...\"",
            style = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Cursive,
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Text(
            text = "\"...magic happens!\"",
            style = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Cursive,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
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
