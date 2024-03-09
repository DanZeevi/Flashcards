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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import org.koin.androidx.compose.koinViewModel

interface LiteralListActions {
    fun deleteLiteral(literal: Literal)
    fun addLiteral(literal: Literal)
    fun showDialogUpdateLiteral(literal: Literal)
    fun showDialogAddLiteral(value: String = "")
    fun updateLiteral(literal: Literal)
    fun closeDialogAddLiteral()
    fun filter(query: String)
}

@Composable
fun LiteralListScreen(viewModel: LiteralListViewModel = koinViewModel()) {
    val literals by viewModel.literals.observeAsState(listOf())
    val showDialogWithValue by viewModel.dialogState.observeAsState(ShowDialogWithValue(false))
    Column(Modifier.fillMaxSize()) {
        TopBar(filterByQuery = viewModel::filter)
        LiteralListContent(literals, viewModel, showDialogWithValue)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar(
    filterByQuery: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
        value = query,
        onValueChange = {
            query = it
            filterByQuery(it)
        },
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun LiteralListContent(
    literals: List<Literal>,
    actions: LiteralListActions,
    showDialogWithValue: ShowDialogWithValue
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.align(Alignment.TopCenter)) {
            LiteralList(
                literals = literals,
                deleteLiteral = actions::deleteLiteral,
                startEdit = actions::showDialogUpdateLiteral
            )
        }
        AddLiteralButton(
            Modifier.align(Alignment.BottomEnd),
            onClick = actions::showDialogAddLiteral
        )
        with(showDialogWithValue) {
            if (shouldShow && literal != null) {
                AddLiteralDialog(
                    literal,
                    onFinish = {
                        if (isEdit) {
                            actions.updateLiteral(it)
                        } else {
                            actions.addLiteral(it)
                        }
                    },
                    onDismiss = actions::closeDialogAddLiteral
                )
            }
        }
    }
}

@Composable
fun AddLiteralButton(modifier: Modifier = Modifier, onClick: (() -> Unit)?) {
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        onClick = { onClick?.invoke() }
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