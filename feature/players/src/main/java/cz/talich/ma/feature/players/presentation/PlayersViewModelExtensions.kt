package cz.talich.ma.feature.players.presentation

import cz.talich.ma.library.datanba.model.Player

internal fun PlayersViewModel.State.toLoading() = copy(
    isLoading = true,
    error = null,
)

internal fun PlayersViewModel.State.toSuccess(players: List<Player>) = copy(
    isLoading = false,
    error = null,
    players = players,
)

internal fun PlayersViewModel.State.toError(error: String) = copy(
    isLoading = false,
    error = error,
)

internal fun PlayersViewModel.State.consumeError() = copy(
    error = null,
)

internal val Player.name: String
    get() = "$firstName $lastName"
