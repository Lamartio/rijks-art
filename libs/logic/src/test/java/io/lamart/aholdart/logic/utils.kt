package io.lamart.aholdart.logic

/**
 * The 'suspending' means that it spies suspend functions. This is necessary for overloading
 * The '1' in the name refers to 1 argument. This is necessary for overloading
 *
 * @return a lambda and a list with the results of the lambda
 */

fun <A, R> suspendingLambdaSpy1(block: suspend (A) -> R): Pair<List<Pair<A, Result<R>>>, suspend (A) -> R> {
    val list = mutableListOf<Pair<A, Result<R>>>()
    val lambda: suspend (A) -> R = { a ->
        runCatching { block(a) }
            .also { list.add(a to it) }
            .getOrThrow()
    }

    return list to lambda
}