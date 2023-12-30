package com.danzeevi.flashcards.ui.literal_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.ui.flashcard.Flashcard
import org.koin.androidx.compose.getViewModel

@Composable
fun LiteralListScreen(viewModel: LiteralListViewModel = getViewModel()) {
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
            if (shouldShow && literal != null) {
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
