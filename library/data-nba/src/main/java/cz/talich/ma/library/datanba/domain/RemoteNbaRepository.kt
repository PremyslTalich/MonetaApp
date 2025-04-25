package cz.talich.ma.library.datanba.domain

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.usecase.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RemoteNbaRepository {
    val fetchState: StateFlow<Data<Unit>>
    fun setFetchState(state: Data<Unit>)

    suspend fun fetchMorePlayers() : Flow<Data<List<Player>>>
}
