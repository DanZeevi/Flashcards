package com.danzeevi.flashcards

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danzeevi.flashcards.ui.MainViewModel
import com.danzeevi.flashcards.ui.Screen.List
import com.danzeevi.flashcards.ui.Screen.Test
import com.danzeevi.flashcards.ui.bottom_navbar.BottomNavBar
import com.danzeevi.flashcards.ui.literal_list.LiteralList
import com.danzeevi.flashcards.ui.literal_list.LiteralListScreen
import com.danzeevi.flashcards.ui.theme.FlashcardsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsTheme {
                Surface(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    MainContent(viewModel)
                }
            }
        }
        if (savedInstanceState === null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            val text = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT)
            text?.let {
//                viewModel.showDialogAddLiteral(it)
            }
        }
    }

    @Composable
    fun MainContent(viewModel: MainViewModel) {
        val screen by viewModel.currentScreen.observeAsState(List)

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .weight(1f), verticalAlignment = Alignment.Top) {
                when (screen) {
                    List -> LiteralListScreen()
                    Test -> TODO("Not yet implemented") // TestScreen()
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                BottomNavBar(screen, viewModel::setScreen)
            }
        }
    }
}

@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewList() {
    FlashcardsTheme {
        Surface {
            LiteralList(vocabularySample, {}) {}
        }
    }
}
