package cz.talich.ma.feature.players.presentation

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PlayersViewModelExtensionsTest {
    private val defaultState = PlayersViewModel.State()

    @Test
    fun `should switch state to loading`() = runTest {
        assertSoftly(defaultState.toLoading()) {
            isLoading shouldBe true
            error shouldBe null
        }
    }

    @Test
    fun `should switch state to success`() = runTest {
        val newPlayers: List<Player> = listOf(
            mockk<Player> { every { id } returns PlayerId(1) },
            mockk<Player> { every { id } returns PlayerId(2) },
            mockk<Player> { every { id } returns PlayerId(3) },
        )

        assertSoftly(defaultState.toSuccess(newPlayers)) {
            players shouldBe newPlayers
            isLoading shouldBe false
            error shouldBe null
        }
    }

    @Test
    fun `should switch state to error`() = runTest {
        assertSoftly(defaultState.toError("Something went wrong")) {
            isLoading shouldBe false
            error shouldBe "Something went wrong"
        }
    }

    @Test
    fun `should switch state to error consumed`() = runTest {
        assertSoftly(defaultState.consumeError()) {
            error shouldBe null
        }
    }

    @Test
    fun `should return player's full name`() = runTest {
        val player = mockk<Player> {
            every { firstName } returns "John"
            every { lastName } returns "Doe"
        }

        player.name shouldBe "John Doe"
    }
}
