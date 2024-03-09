package com.danzeevi.flashcards.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danzeevi.flashcards.common.TimeHandler
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

class TestViewModel(
    private val repo: LiteralRepository,
    private val timeHandler: TimeHandler
) : ViewModel() {
    private val _literals = MutableStateFlow<List<Literal>>(emptyList())

    private val _currentLiteral = MutableStateFlow<Literal?>(null)
    val currentLiteral = _currentLiteral.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getLiteralsFilteredForToday().collect {
                _literals.value = it
                _currentLiteral.value = it.firstOrNull()
            }
        }
    }

    private fun fetchNextLiteral() {
        val newList = _literals.value.drop(1)
        val item = newList.firstOrNull() ?: return
        _currentLiteral.value = item
        _literals.value = newList
    }

    fun markCurrentLiteralAndFetchNext(known: Boolean) {
        currentLiteral.value?.run literal@{
            val updatedInterval =
                if (known) {
                    when (interval) {
                        in 1..Int.MAX_VALUE -> interval * 2
                        else -> 2
                    }
                } else {
                    1
                }
            interval = updatedInterval
            nextViewDate = calculateNextViewTime(updatedInterval)
            viewModelScope.launch(Dispatchers.IO) {
                repo.update(this@literal)
            }
        }
        fetchNextLiteral()
    }

    private fun calculateNextViewTime(interval: Int): Long =
        timeHandler.getCurrentTimeMs() + interval.days.inWholeMilliseconds
}