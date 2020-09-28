package main.sorts.util

/**
 * Realisation of For-function with 0..List.size as range
 */
fun <T, R> List<T>.forLen(deltaStart: Int = 0, deltaEnd: Int = 0, iteration: List<T>.(Int) -> R) {
    for (i in (0 + deltaStart) until (size + deltaEnd)) iteration(i)
}

/**
 * Swap function for MutableList
 *
 * @param [i1]  first index to swap
 * @param [i2] second index to swap
 *
 * @return set result
 */
fun <T> MutableList<T>.swap(i1: Int, i2: Int) = set(i1, get(i2).also { set(i2, get(i1)) })
