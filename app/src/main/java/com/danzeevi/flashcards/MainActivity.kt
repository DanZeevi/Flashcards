package com.danzeevi.flashcards

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danzeevi.flashcards.ui.theme.FlashcardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LiteralList(literals = vocabularySample)
                }
            }
        }
    }
}

data class Literal(val value: String, val definition: String)

@Composable
fun LiteralList(literals: List<Literal>) {
    LazyColumn {
        items(literals) { literal ->
            FlashCard(literal)
        }
    }
}

@Composable
fun FlashCard(literal: Literal) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotation =
        animateFloatAsState(
            targetValue = if (isFlipped) 180f else 0f,
            label = "flip",
            animationSpec = TweenSpec(600, easing = EaseInOutQuad)
        )

    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = Modifier
        .clickable(
            interactionSource,
            indication = null
        )
        {
            isFlipped = !isFlipped
        }) {
        if (rotation.value < 90f) {
            CardFace(literal.value, rotation.value)
        } else {
            CardFace(literal.definition, rotation.value, false)
        }
    }
}

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

@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewList() {
    FlashcardsTheme {
        Surface {
            LiteralList(vocabularySample)
        }
    }
}