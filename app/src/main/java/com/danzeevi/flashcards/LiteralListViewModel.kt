package com.danzeevi.flashcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ShowDialogWithValue(
    val shouldShow: Boolean,
    val literal: Literal? = null,
    val isEdit: Boolean = false,
)

class LiteralListViewModel(private val literalRepo: LiteralRepository) : ViewModel() {
    private val _dialogState = MutableLiveData(ShowDialogWithValue(false))
    val dialogState: LiveData<ShowDialogWithValue> = _dialogState

    val literals: LiveData<List<Literal>> = literalRepo.getAll()

    fun showDialogAddLiteral(value: String = "") {
        _dialogState.value = ShowDialogWithValue(true, Literal(value, ""))
    }

    fun showDialogUpdateLiteral(literal: Literal) {
        _dialogState.value = ShowDialogWithValue(true, literal, true)
    }

    fun closeDialogAddLiteral() {
        _dialogState.value = ShowDialogWithValue(false)
    }

    fun addLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.insert(literal)
        }
    }

    fun updateLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.update(literal)
        }
    }

    fun deleteLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.delete(literal)
        }
    }
}