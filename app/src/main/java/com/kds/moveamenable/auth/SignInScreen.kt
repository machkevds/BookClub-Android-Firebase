package com.kds.moveamenable.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignInScreen(onSignedIn: () -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { res ->
        val response = IdpResponse.fromResultIntent(res.data)
        if (res.resultCode == Activity.RESULT_OK) {
            onSignedIn()
        }
    }

    val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Ryot Tracker")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            launcher.launch(signInIntent)
        }) {
            Text("Sign in with Google")
        }
    }
}
