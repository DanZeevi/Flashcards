package com.danzeevi.flashcards.di

import com.danzeevi.flashcards.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>()
}