package main.sorts.realisation

import main.sorts.abstraction.Sort
import main.sorts.util.forLen
import main.sorts.util.swap

/**
 * Data to receive in callback when sort's condition is reached
 *
 * @return Pair of Compared elements<element, index>
 */
typealias SelectionConditionReachResult<T> = Pair<Pair<T, Int>, Pair<T, Int>>

/**
 * Data to receive in callback when something changed during iteration
 *
 * @return Pair of swapped pairs<element, index>
 */
typealias SelectionChangesResult<T> = Pair<Pair<T, Int>, Pair<T, Int>>

/**
 * Data to receive in callback when sort's iteration completed
 *
 * @return List of elements for current iteration
 */
typealias SelectionIterationResult<T> = List<T>

/**
 * Data to receive in callback when sort's iteration completed and nothing changed
 */
typealias SelectionNothingChangesResult = Unit

/**
 * Realisation of Selection sorting [list] with reporters from Sort class
 *
 * @param [Sortable] the type of elements to sorting
 * @property [list] the list of elements to sort
 */
class SelectionSort<Sortable: Comparable<Sortable>>(
        list: List<Sortable>
) : Sort<
        Sortable,
        SelectionConditionReachResult<Sortable>,
        SelectionChangesResult<Sortable>,
        SelectionIterationResult<Sortable>,
        SelectionNothingChangesResult
>(list) {
    override var list = list.toMutableList()

    /**
     * Find index of minimum element by special condition
     *
     * @param [startFrom] starting index
     * @param [condition] comparing condition
     *
     * @return index of minimum element by special condition
     */
    private fun List<Sortable>.indexOfMin(startFrom: Int = 0, condition: (Sortable, Sortable) -> Boolean): Int {
        var minI = 0
        var minValue = list[0]

        this.subList(startFrom, size) // TODO: create Foreach-functions with Range argument
            .forEachIndexed { index, item ->
                if (condition(minValue, item)) {
                    onConditionReached((minValue to minI) to (item to index)) // reporting
                    minI = index
                    minValue = item
                }
            }

        return minI
    }

    /**
     * Sort [list] by Selection sort
     *
     * Sorting [list] and calls reporters
     *
     * @return sorted [list]
     */
    override fun sort(condition: (Sortable, Sortable) -> Boolean): List<Sortable> {
        list.forLen { i -> // main sort For-cycle
            this as MutableList
            onNodeReached(list[i], i) // reporting

            val minI = list.indexOfMin(i, condition)

            onIterationEnd(list) // reporting

            if (minI != i) {
                onSomethingChanged((list[minI] to minI) to (list[i] to i)) // reporting
                this.swap(i, minI)
            } else {
                onNothingChanged(Unit) // reporting
            }
        }

        return list
    }
}