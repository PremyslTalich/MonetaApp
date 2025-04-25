package cz.talich.ma.library.datanba.data

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import cz.talich.ma.library.test.infrastructure.AbstractTest
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class InMemoryNbaRepositoryTest : AbstractTest() {

    private val repository = InMemoryNbaRepository()

    @Test
    fun `should have no data`() {
        repository.players shouldBe emptyMap()
    }

    @Test
    fun `should add new players`() = runTest {
        val newPlayers = listOf(
            mockk<Player> { every { id } returns PlayerId(1) },
            mockk<Player> { every { id } returns PlayerId(2) },
            mockk<Player> { every { id } returns PlayerId(3) },
        )

        repository.addPlayers(newPlayers)

        repository.players shouldBe mapOf(
            PlayerId(1) to newPlayers[0],
            PlayerId(2) to newPlayers[1],
            PlayerId(3) to newPlayers[2],
        )
    }

    @Test
    fun `should overwrite already existing players`() = runTest {
        val newPlayers = listOf(
            mockk<Player> { every { id } returns PlayerId(1) },
            mockk<Player> {
                every { id } returns PlayerId(2)
                every { firstName } returns "Old"
            },
            mockk<Player> { every { id } returns PlayerId(3) },
        )
        val alreadyExistingPlayer = listOf(
            mockk<Player> {
                every { id } returns PlayerId(2)
                every { firstName } returns "New"
            }
        )

        repository.addPlayers(newPlayers)
        repository.addPlayers(alreadyExistingPlayer)

        repository.players[PlayerId(2)]?.firstName shouldBe "New"
    }
}