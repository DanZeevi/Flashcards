package com.danzeevi.flashcards.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danzeevi.flashcards.data.Literal
import com.danzeevi.flashcards.data.LiteralRepository
import kotlin.time.Duration.Companion.days

class TestViewModel(private val repo: LiteralRepository) : ViewModel() {
    val liveLiterals = repo.getAll()

    private var literals: MutableList<Literal> =
        repo.getAll().value?.toMutableList() ?: mutableListOf() // TODO: filter and order literals by view date
    val currentLiteral = MutableLiveData<Literal?>(literals.removeFirstOrNull())

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
        getNext()
    }

    fun markLiteralUnknown() {
        currentLiteral.value?.let { literal ->
            literal.interval = 1
            literal.nextViewDate = calculateNextViewTime(1)
            repo::update
        }
        getNext()
    }

    private fun getNext() {
        if (literals.isEmpty() && liveLiterals.value?.isNotEmpty() == true) {
            literals = liveLiterals.value?.toMutableList() ?: mutableListOf()
        }
        currentLiteral.value = literals.removeFirstOrNull()
    }

    private fun calculateNextViewTime(interval: Int): Long =
        System.currentTimeMillis() + interval.days.inWholeMilliseconds
}