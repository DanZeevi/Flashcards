package com.danzeevi.flashcards.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

class TestViewModel(private val repo: LiteralRepository) : ViewModel() {
    private val _literals = MutableStateFlow<List<Literal?>>(emptyList())

    private val _currentLiteral = MutableStateFlow<Literal?>(null)
    val currentLiteral = _currentLiteral.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect {
                _literals.value = it
                _currentLiteral.value = it.firstOrNull()
            }
        }
    }

    private fun fetchLiteral() {
        val newList = _literals.value.drop(1)
        val item = newList.firstOrNull() ?: return
        _currentLiteral.value = item
        _literals.value = newList
    }

    fun markLiteralKnown() {
        currentLiteral.value?.let { literal ->
            val updatedInterval = when (literal.interval) {
                in 1..Int.MAX_VALUE -> literal.interval * 2
                else -> 2
            }
            literal.interval = updatedInterval
            literal.nextViewDate = calculateNextViewTime(updatedInterval)
            repo::update
        }
        fetchLiteral()
    }

    fun markLiteralUnknown() {
        currentLiteral.value?.let { literal ->
            literal.interval = 1
            literal.nextViewDate = calculateNextViewTime(1)
            repo::update
        }
        fetchLiteral()
    }

    private fun calculateNextViewTime(interval: Int): Long =
        System.currentTimeMillis() + interval.days.inWholeMilliseconds
}