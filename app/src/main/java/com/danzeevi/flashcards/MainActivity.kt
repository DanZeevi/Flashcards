package com.danzeevi.flashcards

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import com.danzeevi.flashcards.ui.theme.FlashcardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsTheme {
                Surface(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
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
    LazyColumn(
        Modifier.background(Color.Transparent)
    ) {
        items(literals) { literal ->
            Flashcard(literal)
        }
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