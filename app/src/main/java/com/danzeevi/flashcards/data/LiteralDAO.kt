package com.danzeevi.flashcards.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LiteralDAO {
    @Insert
    fun insert(literal: Literal)

    @Query("SELECT * FROM Literal WHERE LOWER(value) LIKE LOWER(:query) OR LOWER(definition) LIKE LOWER(:query)")
    fun getFiltered(query: String): Flow<List<Literal>>

    @Update
    fun update(literal: Literal)

    @Delete
    fun delete(literal: Literal)

    @Query("SELECT * FROM Literal WHERE nextViewDate < :currentMillis")
    fun getLiteralsBeforeNow(currentMillis: Long): Flow<List<Literal>>
}