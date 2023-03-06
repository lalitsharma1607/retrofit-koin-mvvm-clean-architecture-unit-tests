package com.sharma.mymeal.presentation

import android.app.Application
import com.sharma.mymeal.koin.UseCaseDependency
import com.sharma.mymeal.koin.appModule
import com.sharma.mymeal.koin.repoModule
import com.sharma.mymeal.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    repoModule,
                    viewModelModule,
                    UseCaseDependency,
//                    detailViewModelModule
                )
            )
        }
    }
}