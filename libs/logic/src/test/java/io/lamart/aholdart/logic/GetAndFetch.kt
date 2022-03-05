package io.lamart.aholdart.logic

import com.google.common.truth.Truth.assertThat
import io.lamart.aholdart.logic.utils.getAndFetch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

fun <A, R> suspendingLambdaSpy1(block: suspend (A) -> R): Pair<List<Pair<A, Result<R>>>, suspend (A) -> R> {
    val list = mutableListOf<Pair<A, Result<R>>>()
    val lambda: suspend (A) -> R = { a ->
        runCatching { block(a) }
            .also { list.add(a to it) }
            .getOrThrow()
    }

    return list to lambda
}

class GetAndFetch {

    @Test
    fun `getting and fetching fails`() = runBlocking {
        // Arrange
        val (getCalls, get) = suspendingLambdaSpy1<Int, String> { throw Throwable("Disk exploded") }
        val (setCalls, set) = suspendingLambdaSpy1<String, Unit> { }
        val (fetchCalls, fetch) = suspendingLambdaSpy1<Int, String> { throw Throwable("Network exploded") }
        val getAndFetchString = getAndFetch(get, set, fetch)
        // Act
        val result = getAndFetchString(1).toList()
        // Assert
        assertThat(result).hasSize(0)
        assertThat(getCalls).hasSize(1)
        assertThat(setCalls).hasSize(0)
        assertThat(fetchCalls).hasSize(1)
    }

    @Test
    fun `getting succeeds, but fetching fails`() = runBlocking {
        // Arrange
        val (getCalls, get) = suspendingLambdaSpy1<Int, String> { it.toString() }
        val (setCalls, set) = suspendingLambdaSpy1<String, Unit> { }
        val (fetchCalls, fetch) = suspendingLambdaSpy1<Int, String> { throw Throwable("Network exploded") }
        val getAndFetchString = getAndFetch(get, set, fetch)
        // Act
        val result = getAndFetchString(1).toList()
        // Assert
        assertThat(result).hasSize(1)
        assertThat(getCalls).hasSize(1)
        assertThat(setCalls).hasSize(0)
        assertThat(fetchCalls).hasSize(1)
    }

    @Test
    fun `getting fails and fetching succeeds`() = runBlocking {
        // Arrange
        val (getCalls, get) = suspendingLambdaSpy1<Int, String> { throw Throwable("Disk exploded") }
        val (setCalls, set) = suspendingLambdaSpy1<String, Unit> { }
        val (fetchCalls, fetch) = suspendingLambdaSpy1<Int, String> { it.toString() }
        val getAndFetchString = getAndFetch(get, set, fetch)
        // Act
        val result = getAndFetchString(1).toList()
        // Assert
        assertThat(result).hasSize(1)
        assertThat(getCalls).hasSize(1)
        assertThat(setCalls).hasSize(1)
        assertThat(fetchCalls).hasSize(1)
    }

    @Test
    fun `getting and fetching succeeds`() = runBlocking {
        // Arrange
        val (getCalls, get) = suspendingLambdaSpy1<Int, String> { it.toString() }
        val (setCalls, set) = suspendingLambdaSpy1<String, Unit> { }
        val (fetchCalls, fetch) = suspendingLambdaSpy1<Int, String> { it.toString() }
        val getAndFetchString = getAndFetch(get, set, fetch)
        // Act
        val result = getAndFetchString(1).toList()
        // Assert
        assertThat(result).hasSize(2)
        assertThat(getCalls).hasSize(1)
        assertThat(setCalls).hasSize(1)
        assertThat(fetchCalls).hasSize(1)
    }

}