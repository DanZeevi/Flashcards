package com.danzeevi.flashcards

import android.app.Application
import com.danzeevi.flashcards.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlashcardsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            androidLogger()
            androidContext(this@FlashcardsApp)
            modules(appModule)
        }
    }
}