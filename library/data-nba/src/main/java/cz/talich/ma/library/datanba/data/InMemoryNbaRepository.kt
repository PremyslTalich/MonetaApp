package cz.talich.ma.library.datanba.data

import cz.talich.ma.library.datanba.domain.LocalNbaRepository
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class InMemoryNbaRepository : LocalNbaRepository {
    private val mutex = Mutex()

    // Using map here to have simple & fast lookup based on player id
    private val _players = mutableMapOf<PlayerId, Player>()
    override val players: Map<PlayerId, Player> = _players

    override suspend fun addPlayers(newPlayers: List<Player>) {
        mutex.withLock {
            _players.putAll(newPlayers.associateBy { it.id })
        }
    }
}
