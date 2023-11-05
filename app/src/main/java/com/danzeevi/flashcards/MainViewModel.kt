package com.danzeevi.flashcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ShowDialogWithValue(val shouldShow: Boolean, val literal: String = "")

class MainViewModel : ViewModel() {
    private val _dialogState = MutableLiveData(ShowDialogWithValue(false))
    val dialogState: LiveData<ShowDialogWithValue> = _dialogState

    private val _literals = MutableLiveData<List<Literal>>(vocabularySample)
    val literals: LiveData<List<Literal>> = _literals

    fun showDialogAddLiteral(value: String = "") {
        _dialogState.value = ShowDialogWithValue(true, value)
    }

    fun closeDialogAddLiteral() {
        _dialogState.value = ShowDialogWithValue(false)
    }

    fun addLiteral(value: String, definition: String) {
        val oldLiterals = _literals.value
        val newLiterals = oldLiterals?.let {
            it + Literal(value, definition)
        } ?: listOf(Literal(value, definition))
        _literals.value = newLiterals
    }
}