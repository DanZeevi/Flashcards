package com.danzeevi.flashcards.ui.literal_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danzeevi.flashcards.common.AppEvent
import com.danzeevi.flashcards.common.EventBus
import com.danzeevi.flashcards.common.collectEvents
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ShowDialogWithValue(
    val shouldShow: Boolean,
    val literal: Literal? = null,
    val isEdit: Boolean = false,
)

class LiteralListViewModel(
    private val literalRepo: LiteralRepository,
    eventBus: EventBus
) : ViewModel() {
    private val _dialogState = MutableLiveData(ShowDialogWithValue(false))
    val dialogState: LiveData<ShowDialogWithValue> = _dialogState

    val literals: LiveData<List<Literal>> = literalRepo.getAll().asLiveData()

    init {
        collectEvents(eventBus) { event ->
            when (event) {
                is AppEvent.DeepLinkAddValue -> {
                    showDialogAddLiteral(event.string)
                }

                AppEvent.EmptyEvent -> {/* No-op */
                }
            }
        }
    }

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