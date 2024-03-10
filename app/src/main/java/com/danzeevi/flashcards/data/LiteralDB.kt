package com.danzeevi.flashcards.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Literal::class], version = 2)
abstract class LiteralDB: RoomDatabase() {
    abstract fun literalDao(): LiteralDAO
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Literal ADD COLUMN example TEXT")
    }
}