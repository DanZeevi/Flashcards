package com.danzeevi.flashcards.ui.flashcard

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.cardface.CardFace

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Flashcard(
    literal: Literal,
    deleteLiteral: ((Literal) -> Unit)? = null,
    startEdit: () -> Unit = {}
) {
    var isFlipped by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = literal, block = {
        isFlipped = false
    })

    val onDelete = deleteLiteral?.let {
        { it.invoke(literal) }
    }

    val rotation by
    animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "flip",
        animationSpec = TweenSpec(600, easing = EaseInOutQuad)
    )

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .combinedClickable(
                onClick = { isFlipped = !isFlipped },
                onLongClick = startEdit,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        if (rotation < 90f) {
            CardFace(literal.value, rotation, onDelete, true, literal.example)
        } else {
            CardFace(literal.definition, rotation, onDelete, false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Flashcard(literal = Literal("value", "definition"))
}