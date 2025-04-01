package hn.single.server.kotlintest

import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    //ThreadingTestNoCoroutine().tryToTest()
    //ThreadingTest().tryToTest()
    //ThreadingVolatileTest().tryToTest()
    //ThreadingTestThreadSafe().tryToTest()
    //ThreadingTestUseLock().tryToTest()
    //ThreadingTestUseLockUseConfinement().tryToTest()
    ThreadingTestSwitchContext().tryToTest()
}

/**
 * Xử lý vấn đề liên quan đến thread safe khi làm việc multithreading
 * https://archive.is/OCZF1
 * https://medium.com/@baka3k/shared-mutable-state-concurrency-74cea98e3598
 */

const val N = 100  // number of coroutines to launch
const val K = 1000 // times an action is repeated by each coroutine

class ThreadingTestNoCoroutine {

    private var counter = 0
    private val data = mutableListOf<Int>()

    private fun massiveRun(action: () -> Unit) {
        val time = measureTimeMillis {
            repeat(N) {
                repeat(K) {
                    action()
                }
            }
        }
        println("NoCoroutine - Completed ${N * K} actions in $time ms")
    }

    fun tryToTest() {
        massiveRun {
            counter++
            data.add(0)
        }
        println("NoCoroutine - Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

class ThreadingTest {
    private var counter = 0
    private val data = mutableListOf<Int>()

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("Normal - Completed ${N * K} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
                data.add(0)
            }
        }
        println("Normal - Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

class ThreadingVolatileTest {

    @Volatile // in Kotlin `volatile` is an annotation - Not correct in this case
    private var counter = 0
    private val data = mutableListOf<Int>()

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("Volatile - Completed ${N * K} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
                data.add(0)
            }
        }
        println("Volatile - Counter = $counter --- dataSize = ${data.size}")
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
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("synchronizedCollection - Completed ${N * K} actions in $time ms")
    }

    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.incrementAndGet()
                data.add(0)
            }
        }
        println("synchronizedCollection - Counter = $counter --- dataSize = ${data.size}")
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
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("Mutex - Completed ${N * K} actions in $time ms")
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
        println("Mutex - Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

//3: Thread confinement
@OptIn(ObsoleteCoroutinesApi::class)
class ThreadingTestUseLockUseConfinement {
    private var counter = 0
    private val data = mutableListOf<Int>()

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val counterContext = newSingleThreadContext("CounterContext")

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("newSingleThreadContext - Completed ${N * K} actions in $time ms")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun tryToTest() {
        withContext(counterContext) { // single-threaded context
            massiveRun {
                counter++
                data.add(0)
            }
        }
        println("newSingleThreadContext - Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}

class ThreadingTestSwitchContext {

    private var counter = 0
    private val data = mutableListOf<Int>()

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val counterContext = newSingleThreadContext("CounterContext")

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(N) {
                    launch {
                        repeat(K) {
                            action()
                        }
                    }
                }
            }
        }
        println("SwitchContext - Completed ${N * K} actions in $time ms")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun tryToTest() {
        withContext(Dispatchers.Default) {
            massiveRun {
                withContext(counterContext) { // switch to another dispatcher
                    counter++
                    data.add(0)
                }
            }
        }
        println("SwitchContext - Counter = $counter --- dataSize = ${data.size}")
        counter = 0
        data.clear()
    }
}
/**
 * Result:
 * NoCoroutine - Completed 100000 actions in 3 ms
 * NoCoroutine - Counter = 100000 --- dataSize = 100000
 * Normal - Completed 100000 actions in 22 ms
 * Normal - Counter = 24869 --- dataSize = 22402
 * Volatile - Completed 100000 actions in 39 ms
 * Volatile - Counter = 72325 --- dataSize = 48742
 * synchronizedCollection - Completed 100000 actions in 38 ms
 * synchronizedCollection - Counter = 100000 --- dataSize = 100000
 * Mutex - Completed 100000 actions in 248 ms
 * Mutex - Counter = 100000 --- dataSize = 100000
 * newSingleThreadContext - Completed 100000 actions in 23 ms
 * newSingleThreadContext - Counter = 100000 --- dataSize = 100000
 * SwitchContext - Completed 100000 actions in 511 ms
 * SwitchContext - Counter = 100000 --- dataSize = 100000
 */