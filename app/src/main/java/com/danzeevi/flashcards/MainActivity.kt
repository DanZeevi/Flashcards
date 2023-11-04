package com.danzeevi.flashcards

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import com.danzeevi.flashcards.ui.theme.FlashcardsTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var literals = mutableStateOf<List<Literal>>(listOf())
    private var showAddLiteralDialog = mutableStateOf(ShowDialogWithValue(false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsTheme {
                Surface(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    MainContent(
                        literals.value, showAddLiteralDialog.value,
                        onClickAddLiteral = mainViewModel::showDialogAddLiteral,
                        addLiteral = mainViewModel::addLiteral,
                        closeDialogAddLiteral = mainViewModel::closeDialogAddLiteral
                    )
                }
            }
        }

        setObservers()
    }

    private fun setObservers() {
        mainViewModel.literals.observe(this) {
            literals.value = it
        }
        mainViewModel.dialogState.observe(this) {
            showAddLiteralDialog.value = it
        }
    }
}

@Composable
fun MainContent(
    literals: List<Literal>, showDialogWithValue: ShowDialogWithValue,
    onClickAddLiteral: () -> Unit,
    addLiteral: (value: String, definition: String) -> Unit,
    closeDialogAddLiteral: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.align(Alignment.TopCenter)) {
            LiteralList(literals = literals)
        }
        AddLiteralButton(
            Modifier.align(Alignment.BottomEnd),
            onClick = onClickAddLiteral
        )
        if (showDialogWithValue.shouldShow) {
            AddLiteralDialog(
                showDialogWithValue.literal,
                onAdd = { value, definition ->
                    addLiteral(value, definition)
                },
                onDismiss = {
                    closeDialogAddLiteral()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddLiteralDialog(
    initialValue: String = "",
    onDismiss: () -> Unit,
    onAdd: (value: String, definition: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var value by rememberSaveable { mutableStateOf(initialValue) }
    var definition by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Column {
            TextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("Value") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            TextField(
                value = definition,
                onValueChange = { definition = it },
                label = { Text("Definition") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onAdd(value, definition)
                        onDismiss()
                    }
                )
            )
        }
    }
}

@Composable
fun AddLiteralButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, "Add literal button")
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