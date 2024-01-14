package com.danzeevi.flashcards.ui.literal_list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danzeevi.flashcards.data.Literal

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddLiteralDialog(
    literal: Literal,
    onDismiss: () -> Unit,
    onFinish: (Literal) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var value by rememberSaveable { mutableStateOf(literal.value) }
    var definition by rememberSaveable { mutableStateOf(literal.definition) }

    Dialog(onDismissRequest = onDismiss) {
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { value = it },
                label = { Text("Value") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = definition,
                onValueChange = { definition = it },
                label = { Text("Definition") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onFinish(literal.also {
                            it.value = value
                            it.definition = definition
                        })
                        onDismiss()
                    }
                )
            )
        }
    }
}

@Preview(
    name = "Light mode empty",
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark mode empty",
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun Preview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AddLiteralDialog(literal = Literal("value", ""), onDismiss = {}, onFinish = {})
    }
}
@Preview(
    name = "Dark mode multi-line",
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMultiLine() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AddLiteralDialog(literal =
        Literal(
            "value of a value of a value of a value of a value of a value of a value",
            "definition of definition of a definition of a definition"),
            onDismiss = {},
            onFinish = {}
        )
    }
}
