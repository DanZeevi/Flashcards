package com.danzeevi.flashcards.ui.flashcard.cardface

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CardFace(text: String, angle: Float, isFront: Boolean = true) {
    Surface(
        shape = MaterialTheme.shapes.large,
        shadowElevation = 1.dp,
        color= if (isFront) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onTertiaryContainer,
        modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
            .height(200.dp)
            .graphicsLayer {
                rotationY = if (isFront) angle else (180 + angle)
                cameraDistance = 8f * density
            }
    ) {
        Text(
            text = text,
            modifier = Modifier
                .wrapContentSize()
                .padding(all = 40.dp),
            color= if (isFront) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
