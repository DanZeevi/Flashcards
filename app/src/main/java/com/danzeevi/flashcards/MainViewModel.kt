package com.danzeevi.flashcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository

data class ShowDialogWithValue(val shouldShow: Boolean, val literal: String = "")

class MainViewModel(private val literalRepo: LiteralRepository) : ViewModel() {
    private val _dialogState = MutableLiveData(ShowDialogWithValue(false))
    val dialogState: LiveData<ShowDialogWithValue> = _dialogState

    val literals: LiveData<List<Literal>> = literalRepo.getAll()

    fun showDialogAddLiteral(value: String = "") {
        _dialogState.value = ShowDialogWithValue(true, value)
    }

    fun closeDialogAddLiteral() {
        _dialogState.value = ShowDialogWithValue(false)
    }

    fun addLiteral(value: String, definition: String) {
        val literal = Literal(value = value, definition = definition)
        literalRepo.insert(literal)
    }
}