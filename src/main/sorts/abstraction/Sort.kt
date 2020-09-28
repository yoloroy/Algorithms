package main.sorts.abstraction

/**
 * Callback value of node reaching
 */
typealias nodeCallback<T> = (item: T, i: Int) -> Unit

/**
 * Summarized data to return
 */
typealias Callback<R> = (result: R) -> Unit

/**
 * Group of *observers* with interface for sorting
 */
abstract class Sort<
        Sortable: Comparable<Sortable>,
        ConditionReachResult,
        ChangesResult,
        IterationResult,
        NothingChangedResult
>(list: List<Sortable>) {
    /**
     * Our list to sorting
     */
    open var list = list.toMutableList()

    /**
     * Callback Function's field
     *
     * @invokes on every reached element
     *
     * @return [Sortable] (node)
     */
    private var onNodeReachedFun: nodeCallback<Sortable>? = null

    /**
     * Callback Function's field
     *
     * @invokes when condition is met
     *
     * @return [ConditionReachResult] (all you want to return)
     */
    private var onConditionReached: Callback<ConditionReachResult>? = null

    /**
     * Callback Function's field
     *
     * @invokes when something changed during the iteration of the algorithm
     *
     * @return [ChangesResult] (all you want to return)
     */
    private var onSomethingChanged: Callback<ChangesResult>? = null

    /**
     * Callback Function's field
     *
     * @invokes when algorithm end it's iteration
     *
     * @return [IterationResult] (all you want to return)
     */
    private var onIterationEnd: Callback<IterationResult>? = null

    /**
     * Callback Function's field
     *
     * @invokes when nothing changed during the iteration of the algorithm
     *
     * @return [NothingChangedResult] (all you want to return)
     */
    private var onNothingChanged: Callback<NothingChangedResult>? = null

    /**
     * Setter of Callback Function
     *
     * @param [value] set's into field with the same name
     *
     * @return this
     */
    fun onNodeReached(value: nodeCallback<Sortable>) = also { onNodeReachedFun = value }

    /**
     * Setter of Callback Function
     *
     * @param [value] set's into field with the same name
     *
     * @return this
     */
    fun onConditionReached(value: Callback<ConditionReachResult>) = also { onConditionReached = value }

    /**
     * Setter of Callback Function
     *
     * @param [value] set's into field with the same name
     *
     * @return this
     */
    fun onSomethingChanged(value: Callback<ChangesResult>) = also { onSomethingChanged = value }

    /**
     * Setter of Callback Function
     *
     * @param [value] set's into field with the same name
     *
     * @return this
     */
    fun onIterationEnd(value: Callback<IterationResult>) = also { onIterationEnd = value }

    /**
     * Setter of Callback Function
     *
     * @param [value] set's into field with the same name
     *
     * @return this
     */
    fun onNothingChanged(value: Callback<NothingChangedResult>) = also { onNothingChanged = value }

    /**
     * Invoker of onNodeReached function
     *
     * @param ([item], [i]) - element and it's index
     */
    protected fun onNodeReached(item: Sortable, i: Int) = onNodeReachedFun?.let { it(item, i) }

    /**
     * Invoker of onConditionReached function
     *
     * @param [result] - result that we want to receive in callback function
     */
    protected fun onConditionReached(result: ConditionReachResult) = onConditionReached?.let { it(result) }

    /**
     * Invoker of onSomethingChanged function
     *
     * @param [result] - result that we want to receive in callback function
     */
    protected fun onSomethingChanged(result: ChangesResult) = onSomethingChanged?.let { it(result) }

    /**
     * Invoker of onIterationEnd function
     *
     * @param [result] - result that we want to receive in callback function
     */
    protected fun onIterationEnd(result: IterationResult) = onIterationEnd?.let { it(result) }

    /**
     * Invoker of onNothingChanged function
     *
     * @param [result] - result that we want to receive in callback function
     */
    protected fun onNothingChanged(result: NothingChangedResult) = onNothingChanged?.let { it(result) }

    /**
     * Abstract Sorting func of [list]
     *
     * Sorting [list] and calls reporters
     *
     * @return sorted [list]
     */
    abstract fun sort(condition: (Sortable, Sortable) -> Boolean): List<Sortable>
}
