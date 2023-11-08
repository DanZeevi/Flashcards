package com.danzeevi.flashcards.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Literal::class], version = 1)
abstract class LiteralDB: RoomDatabase() {
    abstract fun literalDao(): LiteralDAO
}