package com.danzeevi.flashcards.ui.flashcard

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.cardface.CardFace

@Composable
fun Flashcard(literal: Literal) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotation =
        animateFloatAsState(
            targetValue = if (isFlipped) 180f else 0f,
            label = "flip",
            animationSpec = TweenSpec(600, easing = EaseInOutQuad)
        )

    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = Modifier
        .background(Color.Transparent)
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
