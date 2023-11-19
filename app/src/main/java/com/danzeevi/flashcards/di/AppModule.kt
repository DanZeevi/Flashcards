package com.danzeevi.flashcards.di

import androidx.room.Room
import com.danzeevi.flashcards.LiteralListViewModel
import com.danzeevi.flashcards.data.LiteralDAO
import com.danzeevi.flashcards.data.LiteralDB
import com.danzeevi.flashcards.data.LiteralRepository
import com.danzeevi.flashcards.data.LiteralRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val LITERAL_DB = "LiteralDB"

val appModule = module {
    // Literal DB
    single<LiteralDB> {
        Room.databaseBuilder(androidApplication(), LiteralDB::class.java, LITERAL_DB).build()
    }
    single<LiteralDAO> {
        val literalDB = get<LiteralDB>()
        literalDB.literalDao()
    }
    single<LiteralRepository> { LiteralRepositoryImpl(get()) }
    // ViewModels
    viewModel { LiteralListViewModel(get()) }
}