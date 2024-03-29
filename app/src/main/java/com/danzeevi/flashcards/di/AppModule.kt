package com.danzeevi.flashcards.di

import androidx.room.Room
import com.danzeevi.flashcards.ui.literal_list.LiteralListViewModel
import com.danzeevi.flashcards.common.EventBus
import com.danzeevi.flashcards.common.TimeHandler
import com.danzeevi.flashcards.common.TimeHandlerDefault
import com.danzeevi.flashcards.data.LiteralDAO
import com.danzeevi.flashcards.data.LiteralDB
import com.danzeevi.flashcards.data.LiteralRepository
import com.danzeevi.flashcards.data.LiteralRepositoryImpl
import com.danzeevi.flashcards.data.MIGRATION_1_2
import com.danzeevi.flashcards.ui.MainViewModel
import com.danzeevi.flashcards.ui.test.TestViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val LITERAL_DB = "LiteralDB"

val appModule = module {
    // Literal DB
    single<LiteralDB> {
        Room.databaseBuilder(androidApplication(), LiteralDB::class.java, LITERAL_DB)
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<LiteralDAO> {
        val literalDB = get<LiteralDB>()
        literalDB.literalDao()
    }
    single<LiteralRepository> { LiteralRepositoryImpl(get(), get()) }
    // Event Bus
    single { EventBus() }
    // Time keeping
    single<TimeHandler> { TimeHandlerDefault() }
    // ViewModels
    viewModel { MainViewModel(get()) }
    viewModel { LiteralListViewModel(get(), get()) }
    viewModel { TestViewModel(get(), get()) }
}