package com.danzeevi.flashcards.ui.literal_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danzeevi.flashcards.LiteralListViewModel
import com.danzeevi.flashcards.ShowDialogWithValue
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import org.koin.androidx.compose.getViewModel

@Composable
fun LiteralListContainer(viewModel: LiteralListViewModel = getViewModel()) {
    val literals by viewModel.literals.observeAsState(listOf())
    val showDialogWithValue by viewModel.dialogState.observeAsState(ShowDialogWithValue(false))

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.align(Alignment.TopCenter)) {
            LiteralList(
                literals = literals,
                deleteLiteral = viewModel::deleteLiteral,
                startEdit = viewModel::showDialogUpdateLiteral
            )
        }
        AddLiteralButton(
            Modifier.align(Alignment.BottomEnd),
            onClick = viewModel::showDialogAddLiteral
        )
        with(showDialogWithValue) {
            if (shouldShow) {
                AddLiteralDialog(
                    literal,
                    onFinish = {
                        if (isEdit) {
                            viewModel.updateLiteral(it)
                        } else {
                            viewModel.addLiteral(it)
                        }
                    },
                    onDismiss = viewModel::closeDialogAddLiteral
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddLiteralDialog(
    literal: Literal?,
    onDismiss: () -> Unit,
    onFinish: (Literal) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var value by rememberSaveable { mutableStateOf(literal?.value ?: "") }
    var definition by rememberSaveable { mutableStateOf(literal?.definition ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        val focusManager = LocalFocusManager.current
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
                        onFinish(literal?.also {
                            it.value = value
                            it.definition = definition
                        } ?: Literal(value, definition)
                        )
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

@Composable
fun LiteralList(
    literals: List<Literal>,
    deleteLiteral: (Literal) -> Unit,
    startEdit: (Literal) -> Unit
) {
    LazyColumn(
        Modifier.background(Color.Transparent)
    ) {
        items(literals) { literal ->
            Flashcard(literal, deleteLiteral) { startEdit(literal) }
        }
    }
}
