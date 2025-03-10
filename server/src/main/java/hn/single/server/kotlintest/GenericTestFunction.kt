package hn.single.server.kotlintest

fun <T> singletonList(item: T): List<T> {
    return listOf(item)
}

fun <T> T.toStringData(): String {
    return this.toString()
}

fun main() {
    println("singletonList = ${singletonList(1)}")
    val demo = "123456"
    println(demo.substring(2))
    println(demo.takeLast(demo.length - 6))
}