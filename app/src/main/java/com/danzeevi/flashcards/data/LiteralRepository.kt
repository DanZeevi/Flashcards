package com.danzeevi.flashcards.data

import kotlinx.coroutines.flow.Flow

interface LiteralRepository {
    fun getFiltered(query: String = ""): Flow<List<Literal>>
    fun insert(literal: Literal)
    suspend fun update(literal: Literal)
    fun delete(literal: Literal)
    fun getLiteralsFilteredForToday(): Flow<List<Literal>>
}