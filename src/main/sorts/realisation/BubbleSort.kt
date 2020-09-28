package main.sorts.realisation

import main.sorts.abstraction.Sort
import main.sorts.util.forLen
import main.sorts.util.swap

/**
 * Data to receive in callback when condition is reached
 *
 * element: Pair<element, Index>
 *
 * @return Pair of swapped *elements
 */
typealias BubbleConditionReachingResult<T> = Array<out Pair<T, Int>>

/**
 * Data to receive in callback when something changed during iteration
 *
 * Swap operation data - Pair of information about swapped elements
 *
 * @return all data of swaps in iteration
 */
typealias BubbleChangesResult<T> = List<Pair<Pair<T, Int>, Pair<T, Int>>>

/**
 * Realisation of Bubble sorting [list] with reporters from Sort class
 *
 * @param [T] the type of elements to sorting
 * @property [list] the list of elements to sort
 */
class BubbleSort<T: Comparable<T>>(list: List<T>) : Sort<T, BubbleConditionReachingResult<T>, BubbleChangesResult<T>, List<T>, Unit>(list) {
    override var list = list.toMutableList()

    /**
     * Sort [list] by Bubble sort
     *
     * Sorting [list] and calling reporters
     *
     * @return sorted [list]
     */
    override fun sort(condition: (T, T) -> Boolean): List<T> {
        fun pair(index: Int) = list[index] to list[index + 1]

        list.forLen { sortedCount -> // every iteration we surely found one last element
            val changes = mutableListOf<Pair<Pair<T, Int>, Pair<T, Int>>>()
            list.forLen(deltaEnd = -sortedCount - 1) { i -> // one iteration over list
                onNodeReached(list[i], i) // reporting
                val (first, second) = pair(i)

                if (condition(first, second)) {
                    onConditionReached(arrayOf(first to i, second to i + 1)) // reporting
                    changes += (first to i) to (second to i + 1)

                    list.swap(i, i + 1)
                }
            }

            onIterationEnd(list) // reporting

            if (changes.size > 0)
                onSomethingChanged(changes) // reporting
            else
                onNothingChanged(Unit) // reporting
        }

        return list
    }
}

/**
 * Easy initializer of sorting class
 *
 * @return [BubbleSort]
 */
public val <Sortable: Comparable<Sortable>> List<Sortable>.bubbleSort: BubbleSort<Sortable> get() = BubbleSort(this)
