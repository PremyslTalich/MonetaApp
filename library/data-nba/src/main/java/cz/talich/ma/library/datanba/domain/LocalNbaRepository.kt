package cz.talich.ma.library.datanba.domain

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import kotlinx.coroutines.flow.StateFlow

interface LocalNbaRepository {
    val players: Map<PlayerId, Player>
    suspend fun addPlayers(newPlayers: List<Player>)
}
