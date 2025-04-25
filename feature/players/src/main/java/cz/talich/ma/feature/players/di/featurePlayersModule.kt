package cz.talich.ma.feature.players.di

import cz.talich.ma.feature.players.presentation.PlayersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featurePlayersModule = module {
    viewModelOf(::PlayersViewModel)
}