package com.danzeevi.flashcards.ui.bottom_navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.danzeevi.flashcards.ui.Screen

@Composable
fun BottomNavBar(screen: Screen, setScreen: (screen: Screen) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavBarButton(
            isSelected = screen == Screen.List,
            Icons.Filled.List,
            "List"
        ) { setScreen(Screen.List) }
        NavBarButton(
            isSelected = screen == Screen.Test,
            Icons.Filled.AccountBox,
            "Test"
        ) { setScreen(Screen.Test) }
    }
}

@Composable
fun NavBarButton(
    isSelected: Boolean,
    icon: ImageVector,
    contentDescription: String,
    setScreen: () -> Unit
) {
    IconButton(onClick = { setScreen() }) {
        Icon(
            icon,
            contentDescription,
            tint = if (isSelected) Color.White else Color.Gray
        )
    }
}