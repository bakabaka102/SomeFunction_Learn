package hn.single.server

fun <T> singletonList(item: T): List<T> {
    return listOf(item)
}

fun <T> T.toStringData() : String {
    return this.toString()
}

fun main() {
    println("singletonList = ${singletonList(1)}")
}