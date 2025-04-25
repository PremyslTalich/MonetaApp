package cz.talich.ma.system

import android.app.Application
import cz.talich.ma.feature.players.di.featurePlayersModule
import cz.talich.ma.library.datanba.di.libraryDataNbaModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MonetaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidContext(this@MonetaApp)

            modules(
                featurePlayersModule,
                libraryDataNbaModule,
            )
        }
    }
}
