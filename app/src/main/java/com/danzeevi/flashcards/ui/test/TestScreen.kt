package com.danzeevi.flashcards.ui.test

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

const val SLIDE_OUT_ANIM_DURATION = 500

@Composable
fun TestScreen(viewModel: TestViewModel = getViewModel()) {
    val literal by viewModel.currentLiteral.collectAsState()

    fun handleCardMarked(known: Boolean) {
        viewModel.markCurrentLiteralAndFetchNext(known)
    }

    literal?.let {
        TestCard(it) { known ->
            handleCardMarked(known)
        }
    }
}

@Composable
fun TestCard(literal: Literal, markLiteral: (known: Boolean) -> Unit) {
    var isKnown by rememberSaveable { mutableStateOf(false) }
    var shouldShow by rememberSaveable { mutableStateOf(false) }

    fun handleCardMarked(asKnown: Boolean) {
        isKnown = asKnown
        shouldShow = false
    }

    val slideOutAnimSpec =
        tween<IntOffset>(durationMillis = SLIDE_OUT_ANIM_DURATION, easing = FastOutLinearInEasing)

    fun onAnimationEnd() {
        if (!shouldShow) markLiteral(isKnown)
    }

    LaunchedEffect(key1 = shouldShow, block = {
        if (shouldShow) return@LaunchedEffect
        delay(slideOutAnimSpec.durationMillis.toLong() + 100L) // Using the animation duration alone causes a slide-in animation
        onAnimationEnd()
    })
    LaunchedEffect(key1 = literal, block = {
        shouldShow = true
    })

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.animation.AnimatedVisibility(
                shouldShow,
                enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                exit = slideOutHorizontally(slideOutAnimSpec) { it * 2 * (if (isKnown) 1 else -1) }
            ) {
                Flashcard(literal, deleteLiteral = null)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { handleCardMarked(false) }) {
                Icon(Icons.Filled.Close, "Unknown", tint = MaterialTheme.colorScheme.error)
            }
            IconButton(onClick = { handleCardMarked(true) }) {
                Icon(Icons.Filled.Check, "Known", tint = Color.Green)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    TestCard(
        Literal("value", "definition", 0, 1, 0)
    ) {}
}
