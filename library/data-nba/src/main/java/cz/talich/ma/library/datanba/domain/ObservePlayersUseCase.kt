package cz.talich.ma.library.datanba.domain

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.usecase.domain.UseCase
import cz.talich.ma.library.usecase.model.Data
import cz.talich.ma.library.usecase.model.mapSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class ObservePlayersUseCase internal constructor(
    private val localRepository: LocalNbaRepository,
    private val remoteRepository: RemoteNbaRepository,
    private val loadMorePlayersUseCase: LoadMorePlayersUseCase,
) : UseCase<Unit, Flow<Data<List<Player>>>> {
    override fun invoke(params: Unit): Flow<Data<List<Player>>> {
        return remoteRepository.fetchState.mapLatest {
            it.mapSuccess {
                localRepository.players.values.toList()
            }
        }.onStart {
            withContext(Dispatchers.IO) {
                loadMorePlayersUseCase(Unit)
            }
        }
    }
}
