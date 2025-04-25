package cz.talich.ma.library.datanba.data

import cz.talich.ma.library.datanba.domain.RemoteNbaRepository
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.network.data.request
import cz.talich.ma.library.usecase.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RetrofitNbaRepository internal constructor(
    private val nbaApi: NbaApi,
    private val pageSize: Int = PAGE_SIZE, // better for testing
) : RemoteNbaRepository {
    private var cursor: Int = 0

    private val _fetchState = MutableStateFlow<Data<Unit>>(Data.Loading)
    override val fetchState: StateFlow<Data<Unit>> = _fetchState.asStateFlow()

    override fun setFetchState(state: Data<Unit>) {
        _fetchState.value = state
    }

    override suspend fun fetchMorePlayers(): Flow<Data<List<Player>>> {
        return request(
            apiCall = {
                nbaApi.getPlayers(
                    cursor = cursor,
                    pageSize = pageSize,
                )
            },
            transform = {
                cursor = meta.nextCursor

                toModel()
            }
        )
    }

    companion object {
        const val PAGE_SIZE = 35
    }
}
