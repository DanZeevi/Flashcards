package com.danzeevi.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LiteralDAO {
    @Insert
    fun insert(literal: Literal)

    @Query("SELECT * FROM Literal")
    fun getAll(): LiveData<List<Literal>>

    @Update
    fun update(literal: Literal)

    @Delete
    fun delete(literal: Literal)
}