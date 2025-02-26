package hn.single.server.kotlintest

import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    ThreadingTest().tryToTest()
    ThreadingTestThreadSafe().tryToTest()
    ThreadingTestUseLock().tryToTest()
    ThreadingTestUseLockUseConfinement().tryToTest()
}

//https://archive.is/OCZF1
//https://medium.com/@baka3k/shared-mutable-state-concurrency-74cea98e3598

class ThreadingTest {

    //@Volatile // in Kotlin `volatile` is an annotation - Not correct in this case
    private var counter = 0
    private val data = mutableListOf<Int>()

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
                data.add(0)
            }
        }
        println("Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

//Thread-safe data structures
class ThreadingTestThreadSafe {

    private val counter = AtomicInteger() // from Int to AtomicInteger
    private val data: MutableCollection<Int> =
        Collections.synchronizedCollection(mutableListOf<Int>()) // from ArrayList to synchronized ArrayList

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.incrementAndGet()
                data.add(0)
            }
        }
        println("Counter = $counter --- dataSize = ${data.size}")
        counter.set(0)
        data.clear()
    }
}

//Cơ chế lock - Slow, may be DeadLock
class ThreadingTestUseLock {

    private var counter = 0
    private val data = mutableListOf<Int>()
    private val mutex = Mutex()

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                mutex.withLock {
                    counter++
                    data.add(0)
                }
            }
        }
        println("Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

//3: Thread confinement
@OptIn(ObsoleteCoroutinesApi::class)
class ThreadingTestUseLockUseConfinement {

    private var counter = 0
    private val data = mutableListOf<Int>()
    @OptIn(DelicateCoroutinesApi::class)
    private val counterContext = newSingleThreadContext("CounterContext")

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(counterContext) { // single-threaded context
            massiveRun {
                counter++
                data.add(0)
            }
        }
        println("Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}