package com.kds.bookclub.ui.components
//decided to tweak UI some more with a fancy back button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kds.bookclub.ui.theme.PrimaryButtonDefaults
import com.kds.bookclub.ui.theme.elevatedShadow

@Composable
fun FancyBackButton(navController: NavController, destination: String? = null) {
    Button(
        onClick = {
            //if specific destination is provide, navigate there... or popStackback
            destination?.let { navController.navigate(it) } ?: navController.popBackStack()
        },
        colors = PrimaryButtonDefaults(),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(8.dp)
            .elevatedShadow()
    ) {
        Text("← Back", fontWeight = FontWeight.Bold)
    }
}
