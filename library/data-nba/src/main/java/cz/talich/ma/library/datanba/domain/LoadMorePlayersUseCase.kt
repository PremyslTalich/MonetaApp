package cz.talich.ma.library.datanba.domain

import cz.talich.ma.library.usecase.domain.SuspendedUseCase
import cz.talich.ma.library.usecase.model.Data
import cz.talich.ma.library.usecase.model.mapSuccess
import kotlinx.coroutines.flow.collectLatest

class LoadMorePlayersUseCase internal constructor(
    private val localRepository: LocalNbaRepository,
    private val remoteRepository: RemoteNbaRepository,
) : SuspendedUseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit) {
        remoteRepository.fetchMorePlayers().collectLatest {
            if (it is Data.Success) {
                localRepository.addPlayers(it.data)
            }

            remoteRepository.setFetchState(it.mapSuccess { Unit })
        }
    }
}
