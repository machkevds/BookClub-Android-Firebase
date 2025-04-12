package com.kds.moveamenable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kds.moveamenable.ui.navigation.AppNavigation
import com.kds.moveamenable.ui.theme.MoveAmenableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveAmenableTheme {
                AppNavigation()
            }
        }
    }
}