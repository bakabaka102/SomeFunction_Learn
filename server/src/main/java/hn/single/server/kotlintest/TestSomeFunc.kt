package hn.single.server.kotlintest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Hello World!")

    testFlatMap()
    //testTakeIfTakeLess(input)
    //testException()
    println("End test!")
}

private fun testFlatMap() {
    // 1
    listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6)).flatMap {
        it
    }.also {
        println(it) // Output: [1, 2, 3, 4, 5, 6]
    }

    // 2
    val numbers = listOf(1, 2, 3)
    numbers.flatMap { listOf(it, it * 2) }.also {
        println(it) // Output: [1, 2, 2, 4, 3, 6]
    }

    // 3
    val listOfLists = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    listOfLists.flatMap { it }.also {
        println(it) // Output: [1, 2, 3, 4, 5, 6]
    }

    // 4
    val pairs = listOf(Pair(1, "one"), Pair(2, "two"), Pair(3, "three"))
    pairs.flatMap { listOf(it.first, it.second) }.also {
        println(it) // Output: [1, one, 2, two, 3, three]
    }

    // 5
    val input = "Hello"
    input.flatMap {
        listOf(it, it)
    }.also {
        println("after flatMap: $it")
    }
    val result = input.flatMap { listOf(it, it) }
    println(result) // Output: [H, H, e, e, l, l, l, l, o, o]

    val list = buildList {
        add("string")
        add("sht")
        add("string2")
    }
    list.flatMap { listOf(it, it) }.also {
        println("flatMap: $it")
    }


}

private fun testTakeIfTakeLess(input: String) {
    val validated = input.takeIf { it.length >= 3 }
    println(validated) // Hello

    val negativeNumber = (-5).takeUnless { it > 0 }
    println(negativeNumber) // -5
}

private suspend fun testException() {
    CoroutineScope(Dispatchers.Default).launch {
        kotlin.runCatching {
            println("launch divide 3 by 0")
            3 / 0
        }.onFailure {
            println("Exception: ${it.message}")
        }
        println("launch divide 3 by 0")
        3 / 0
    }
    val async = CoroutineScope(Dispatchers.Default).async {
        3 / 0
    }
    runCatching {
        async.await()
    }.onFailure {
        println("Exception: ${it.message}")
    }
}