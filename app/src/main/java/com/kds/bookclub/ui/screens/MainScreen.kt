package com.kds.bookclub.ui.screens
//main screen of app
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.kds.bookclub.ui.theme.PrimaryButtonDefaults
import com.kds.bookclub.ui.theme.elevatedShadow
import com.kds.bookclub.navigation.Routes

@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        // logout Button
        Button(
            onClick = {
                val auth = FirebaseAuth.getInstance()

                auth.signOut()

                //sign out of google account for a 100% sign out
                com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(
                    context,
                    com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
                ).signOut()

                navController.navigate(Routes.SIGN_IN) {
                    popUpTo(0) // Clear back stack
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .elevatedShadow()
        ) {
            Text("Logout")
        }
    }
}
