package com.danzeevi.flashcards.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface LiteralRepository {
    fun getAll(): Flow<List<Literal>>
    fun insert(literal: Literal)
    fun update(literal: Literal)
    fun delete(literal: Literal)
}