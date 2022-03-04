package io.lamart.aholdart.logic

import com.google.common.truth.Truth.assertThat
import io.lamart.aholdart.logic.async.Async
import io.lamart.aholdart.logic.async.initial
import io.lamart.aholdart.optics.compose
import io.lamart.aholdart.optics.lensOf
import io.lamart.aholdart.optics.sourceOf
import kotlinx.coroutines.*
import org.junit.Test

data class State(val downloading: Async<String> = initial())


class ExampleUnitTest {

    private val initial = State()

    @Test
    fun executeAsyncActionSuccessfully() = runBlocking {
        // Arrange
        val scope = this + Job(coroutineContext[Job])
        val result = mutableListOf<State>(initial)
        val source = sourceOf({ result.last() }, result::add)
        val execute = source
            .compose(lensOf({ downloading }, { copy(downloading = it) }))
            .toAsyncAction<Int, String>(
                strategy = latest(flowOfSuspending { it.toString() }),
                scope
            )
        // Act
        execute(123)
        delay(100) // Needs to be refactored, but is being accurate at the moment
        // Assert
        assertThat(result).hasSize(3)
        assertThat(result[0].downloading).isInstanceOf(Async.Initial::class.java)
        assertThat(result[1].downloading).isInstanceOf(Async.Executing::class.java)
        assertThat(result[2].downloading).isInstanceOf(Async.Success::class.java)
        assertThat(result[2].downloading.result).isEqualTo("123")
        scope.cancel()
    }

    @Test
    fun executeAsyncActionFailing() = runBlocking {
        // Arrange
        val scope = this + Job(coroutineContext[Job])
        val result = mutableListOf<State>(initial)
        val source = sourceOf({ result.last() }, result::add)
        val execute = source
            .compose(lensOf({ downloading }, { copy(downloading = it) }))
            .toAsyncAction<Int, String>(
                strategy = latest(flowOfSuspending { throw Throwable("Whoops!") }),
                scope
            )
        // Act
        execute(123)
        delay(100) // Needs to be refactored, but is being accurate at the moment
        // Assert
        assertThat(result).hasSize(3)
        assertThat(result[0].downloading).isInstanceOf(Async.Initial::class.java)
        assertThat(result[1].downloading).isInstanceOf(Async.Executing::class.java)
        assertThat(result[2].downloading).isInstanceOf(Async.Failure::class.java)
        assertThat(result[2].downloading.reason).isNotNull()
        scope.cancel()
    }
}
