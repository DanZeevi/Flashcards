package com.danzeevi.flashcards.ui.test

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import org.koin.androidx.compose.getViewModel

@Composable
fun TestScreen(viewModel: TestViewModel = getViewModel()) {
    val list by viewModel.liveLiterals.observeAsState(listOf())
    val literal by viewModel.currentLiteral.observeAsState()
    literal?.let {
        TestCard(it, viewModel::markLiteralKnown, viewModel::markLiteralUnknown)
    }

    viewModel.liveLiterals.observe(LocalLifecycleOwner.current) {
        if (list.isNotEmpty() && literal == null) {
            viewModel.markLiteralUnknown()
        }
    }
}

@Composable
fun TestCard(literal: Literal, onLiteralKnown: () -> Unit, onLiteralUnknown: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Flashcard(literal, deleteLiteral = { /**/ }, {})
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = { onLiteralUnknown() }) {
                Icon(Icons.Filled.Close, "Unknown", tint = MaterialTheme.colorScheme.error)
            }
            IconButton(onClick = { onLiteralKnown() }) {
                Icon(Icons.Filled.Check, "Known", tint = Color.Green)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    TestCard(
        Literal("value", "definition", 0, 1, 0),
        {},
        {}
    )
}