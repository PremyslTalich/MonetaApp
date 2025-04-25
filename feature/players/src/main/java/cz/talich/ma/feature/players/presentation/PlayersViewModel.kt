package cz.talich.ma.feature.players.presentation

import android.util.Log
import cz.talich.ma.library.datanba.domain.LoadMorePlayersUseCase
import cz.talich.ma.library.datanba.domain.ObservePlayersUseCase
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.mvvm.presentation.AbstractViewModel
import cz.talich.ma.library.usecase.domain.invoke
import cz.talich.ma.library.usecase.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

class PlayersViewModel(
    observePlayers: ObservePlayersUseCase,
    private val loadMorePlayers: LoadMorePlayersUseCase,
) : AbstractViewModel<PlayersViewModel.State>(State()) {
    init {
        launchInVmScope {
            observePlayers().collectLatest(::onPlayers)
        }
    }

    private fun onPlayers(resp: Data<List<Player>>) {
        state = when (resp) {
            Data.Loading -> state.toLoading()
            is Data.Success -> state.toSuccess(resp.data)
            is Data.Error -> state.toError(
                error = resp.exception.message ?: "Some generic error"
            ).also {
                Log.e("PlayersViewModel", "Error loading players", resp.exception)
            }
        }
    }

    fun onLoadMorePlayers() {
        launchInVmScope(Dispatchers.IO) {
            loadMorePlayers()
        }
    }

    fun onErrorConsumed() {
        state = state.consumeError()
    }

    fun onPlayerClick(player: Player) {
        state = state.copy(selectedPlayer = player)
    }

    fun onPlayerDetailClosed() {
        state = state.copy(selectedPlayer = null)
    }

    data class State(
        val isLoading: Boolean = false,

        // would like to have some Error class here, but this will do for the demo
        val error: String? = null,

        val players: List<Player> = emptyList(),
        val selectedPlayer: Player? = null,
    ) : AbstractViewModel.State
}
