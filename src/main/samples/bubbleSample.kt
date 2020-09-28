package main.samples

import main.sorts.realisation.BubbleSort

val String.rx: Regex get() = Regex(this)

fun String.split() = split("\\s".rx)

fun <R> String.readList(converter: String.() -> R) = split().map { converter(it) }

typealias Element = Pair<Any, Int>
val Element.item: Any get() = first
val Element.index: Any get() = second
val Element.asElement: String get() = "$item at $index"
val Pair<Element, Element>.asSwapped: String get() = "(${first.asElement} ♻️ ${second.asElement})"

fun bubbleSample() {
    val arr = readLine()!!.readList { toInt() }

    var iterationCount = 0
    println(
            BubbleSort(arr)
                    .onIterationEnd { iterationCount++ }
                    .onSomethingChanged {
                        println("Iteration $iterationCount: ${it.joinToString(separator = ", ") { swap -> swap.asSwapped }}")
                    }
                    .sort { i1, i2 -> i1 > i2 }
                    .joinToString(" ", prefix = "Result: ")
    )
}
