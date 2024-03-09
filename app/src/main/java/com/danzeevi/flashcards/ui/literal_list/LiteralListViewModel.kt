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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

data class ShowDialogWithValue(
    val shouldShow: Boolean,
    val literal: Literal? = null,
    val isEdit: Boolean = false,
)

const val DEBOUNCE_QUERY_MS = 300L

class LiteralListViewModel(
    private val literalRepo: LiteralRepository,
    eventBus: EventBus
) : ViewModel(), LiteralListActions {
    private val _dialogState = MutableLiveData(ShowDialogWithValue(false))
    val dialogState: LiveData<ShowDialogWithValue> = _dialogState

    private val queryParameter = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val literals = queryParameter
        .debounce(DEBOUNCE_QUERY_MS)
        .flatMapLatest { query ->
            literalRepo.getFiltered(query)
        }
        .asLiveData(viewModelScope.coroutineContext)

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

    override fun showDialogAddLiteral(value: String) {
        _dialogState.value = ShowDialogWithValue(true, Literal(value, ""))
    }

    override fun showDialogUpdateLiteral(literal: Literal) {
        _dialogState.value = ShowDialogWithValue(true, literal, true)
    }

    override fun closeDialogAddLiteral() {
        _dialogState.value = ShowDialogWithValue(false)
    }

    override fun addLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.insert(literal)
        }
    }

    override fun updateLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.update(literal)
        }
    }

    override fun deleteLiteral(literal: Literal) {
        viewModelScope.launch(Dispatchers.IO) {
            literalRepo.delete(literal)
        }
    }

    override fun filter(query: String) {
        queryParameter.value = query
    }
}
