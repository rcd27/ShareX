package ru.filtrabu.sharex.main

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.InterruptedIOException
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupRx()
    }

    private fun setupRx() {
        RxJavaPlugins.setErrorHandler {
            when {
                it is UndeliverableException -> Timber.e(it)
                it is InterruptedIOException -> Timber.e(it)
                it.cause?.cause is InterruptedIOException -> Timber.e(it)
                else -> Timber.e(it, "Unhandled RxJava exception")
            }
        }
    }
}
