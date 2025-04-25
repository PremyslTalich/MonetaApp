package cz.talich.ma.feature.players.presentation

import android.util.Log
import app.cash.turbine.test
import cz.talich.ma.library.datanba.domain.LoadMorePlayersUseCase
import cz.talich.ma.library.datanba.domain.ObservePlayersUseCase
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import cz.talich.ma.library.test.infrastructure.AbstractTest
import cz.talich.ma.library.usecase.domain.invoke
import cz.talich.ma.library.usecase.model.Data
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PlayersViewModelTest : AbstractTest() {

    private fun viewModel(
        observePlayers: ObservePlayersUseCase = mockk(relaxed = true),
        loadMorePlayers: LoadMorePlayersUseCase = mockk(relaxed = true),
    ) = PlayersViewModel(
        observePlayers = observePlayers,
        loadMorePlayers = loadMorePlayers,
    )

    @Before
    fun before() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should observe players and set loading view state`() = runTest {
        val vm = viewModel(
            observePlayers = mockk {
                every { this@mockk.invoke() } returns flowOf(Data.Loading)
            }
        )

        vm.states.test {
            assertSoftly(expectMostRecentItem()) {
                isLoading shouldBe true
            }
        }
    }

    @Test
    fun `should observe players and set success state when receiving new players`() = runTest {
        val newPlayers: List<Player> = listOf(
            mockk<Player> { every { id } returns PlayerId(1) },
            mockk<Player> { every { id } returns PlayerId(2) },
            mockk<Player> { every { id } returns PlayerId(3) },
        )

        val vm = viewModel(
            observePlayers = mockk {
                every { this@mockk.invoke() } returns flowOf(Data.Success(newPlayers))
            }
        )

        vm.states.test {
            assertSoftly(expectMostRecentItem()) {
                isLoading shouldBe false
                players shouldBe newPlayers
                error shouldBe null
            }
        }
    }

    @Test
    fun `should observe players and set error state when receiving new players failed`() = runTest {
        val anyException = Exception("some error")
        val vm = viewModel(
            observePlayers = mockk {
                every { this@mockk.invoke() } returns flowOf(Data.Error(anyException))
            }
        )

        vm.states.test {
            assertSoftly(expectMostRecentItem()) {
                isLoading shouldBe false
                error shouldBe "some error"
            }
        }
    }

    @Test
    fun `should load more players`() = runTest {
        val loadMorePlayersUseCase = mockk<LoadMorePlayersUseCase>(relaxed = true)
        val vm = viewModel(
            loadMorePlayers = loadMorePlayersUseCase
        )

        vm.onLoadMorePlayers()

        coVerify {
            loadMorePlayersUseCase()
        }
    }

    @Test
    fun `should consume error`() = runTest {
        val anyException = Exception("some error")
        val vm = viewModel(
            observePlayers = mockk {
                every { this@mockk.invoke() } returns flowOf(Data.Error(anyException))
            }
        )

        vm.states.test {
            awaitItem().error shouldBe "some error"

            vm.onErrorConsumed()

            awaitItem().error shouldBe null
        }
    }

    @Test
    fun `should set selected player and clear when player detail is closed`() = runTest {
        val player = mockk<Player> { every { id } returns PlayerId(2) }

        val vm = viewModel()

        vm.onPlayerClick(player)

        vm.states.test {
            awaitItem().selectedPlayer shouldBe player

            vm.onPlayerDetailClosed()

            awaitItem().selectedPlayer shouldBe null
        }
    }
}