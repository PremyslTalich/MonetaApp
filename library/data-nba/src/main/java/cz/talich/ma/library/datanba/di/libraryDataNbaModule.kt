package cz.talich.ma.library.datanba.di

import cz.talich.ma.library.datanba.data.AuthorizationInterceptor
import cz.talich.ma.library.datanba.data.InMemoryNbaRepository
import cz.talich.ma.library.datanba.data.NbaApi
import cz.talich.ma.library.datanba.data.RetrofitNbaRepository
import cz.talich.ma.library.datanba.domain.LoadMorePlayersUseCase
import cz.talich.ma.library.datanba.domain.LocalNbaRepository
import cz.talich.ma.library.datanba.domain.ObservePlayersUseCase
import cz.talich.ma.library.datanba.domain.RemoteNbaRepository
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val NBA_URL = "https://api.balldontlie.io"
private val TIMEOUT_DURATION = 30.seconds.toJavaDuration()

val libraryDataNbaModule = module {
    factoryOf(::ObservePlayersUseCase)
    factoryOf(::LoadMorePlayersUseCase)

    single<RemoteNbaRepository> { RetrofitNbaRepository(get()) }
    single<LocalNbaRepository> { InMemoryNbaRepository() }

    single<NbaApi> {
        Retrofit.Builder()
            .baseUrl(NBA_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbaApi::class.java)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())

            // In the "real world scenario", I would put this only into the debug build variant
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

            .connectTimeout(TIMEOUT_DURATION)
            .readTimeout(TIMEOUT_DURATION)
            .writeTimeout(TIMEOUT_DURATION)
            .build()
    }
}
