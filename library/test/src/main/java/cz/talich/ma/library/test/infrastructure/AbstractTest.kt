package cz.talich.ma.library.test.infrastructure

import org.junit.Rule

abstract class AbstractTest {
    @get:Rule
    val coroutinesDispatchersRule = CoroutineDispatcherRule()
}
