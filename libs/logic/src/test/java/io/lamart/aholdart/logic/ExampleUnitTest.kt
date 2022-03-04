package io.lamart.aholdart.logic

import com.google.common.truth.Truth.assertThat
import io.lamart.aholdart.logic.async.Async
import io.lamart.aholdart.optics.compose
import io.lamart.aholdart.optics.lensOf
import io.lamart.aholdart.optics.sourceOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

data class State(val downloading: Async<Int, String> = Async.Initial())


class ExampleUnitTest {

    private val initial = State()

    @Test
    fun executeAsyncActionSuccessfully() = runBlocking {
        // Arrange
        val result = mutableListOf<State>(initial)
        val source = sourceOf({ result.last() }, result::add)
        val focus = source
            .compose(lensOf({ downloading }, { copy(downloading = it) }))
        val execute = asyncActionOf(focus, ofSuspending { it.toString() }, count = 2)
        // Act
        execute(123)
        delay(100) // Needs to be refactored, but is being accurate at the moment
        // Assert
        assertThat(result).hasSize(3)
        assertThat(result[0].downloading).isInstanceOf(Async.Initial::class.java)
        assertThat(result[1].downloading).isInstanceOf(Async.Executing::class.java)
        assertThat(result[2].downloading).isInstanceOf(Async.Success::class.java)
        assertThat(result[2].downloading.result).isEqualTo("123")
    }

    @Test
    fun executeAsyncActionFailing() = runBlocking {
        // Arrange
        val result = mutableListOf<State>(initial)
        val source = sourceOf({ result.last() }, result::add)
        val focus = source
            .compose(lensOf({ downloading }, { copy(downloading = it) }))
        val execute = asyncActionOf(focus, ofSuspending { throw Throwable("Whoops!") }, count = 2)
        // Act
        execute(123)
        delay(100) // Needs to be refactored, but is being accurate at the moment
        // Assert
        assertThat(result).hasSize(3)
        assertThat(result[0].downloading).isInstanceOf(Async.Initial::class.java)
        assertThat(result[1].downloading).isInstanceOf(Async.Executing::class.java)
        assertThat(result[2].downloading).isInstanceOf(Async.Failure::class.java)
        assertThat(result[2].downloading.reason).isNotNull()
    }
}


//return payload => {
//    return of(payload).pipe(
//        concatMap(project),
//        map(result => fulfilledOf<T, R>(payload, result)),
//    catchError(reason => [rejectedOf<T>(payload, reason)]),
//    startWith(pendingOf<T>(payload))
//    );
//}