package com.danzeevi.flashcards.ui.flashcard.cardface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danzeevi.flashcards.ui.theme.FlashcardsTheme

@Composable
fun CardFace(
    text: String,
    angle: Float,
    onDelete: (() -> Unit)? = null,
    isFront: Boolean = true,
    sentence: String? = null,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        shadowElevation = 1.dp,
        color = if (isFront) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onTertiaryContainer,
        modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
            .height(200.dp)
            .graphicsLayer {
                rotationY = if (isFront) angle else (180 + angle)
                cameraDistance = 8f * density
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(all = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .wrapContentSize(),
                    color = if (isFront) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.height(10.dp))
                if (isFront && sentence != null) {
                    Text(
                        text = sentence,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(all = 10.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            onDelete?.let {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onClick = it,
                ) {
                    Icon(Icons.Filled.Delete, "Delete literal button", tint = Color.Red)
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    FlashcardsTheme {
        Surface {
            CardFace("Text", 0f, sentence = "This is an example.")
        }
    }
}